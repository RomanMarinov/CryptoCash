package com.dev_marinov.cryptocash.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.dev_marinov.cryptocash.DataSerializer
import com.dev_marinov.cryptocash.SearchRequest
import androidx.datastore.dataStore
import com.dev_marinov.cryptocash.data.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class ProtoDataStoreRepository (private val context: Context) {

    private val searchRequestStore: Flow<SearchRequest>
        get() = context.dataStore.data

    private val Context.dataStore: DataStore<SearchRequest> by dataStore(
        fileName = "data.proto",
        serializer = DataSerializer
    )

    val dataPreferencesFlow: Flow<SearchRequest> = searchRequestStore
        .catch { exception ->
            // dataStore.data выдает исключение IOException, когда при чтении данных возникает ошибка
            if (exception is IOException) {
                Log.e("333", "Error reading sort order preferences.", exception)
                emit(SearchRequest.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updateDate(date: String) = context.dataStore.updateData { preferences ->
        preferences.toBuilder()
            .setDate(date)
            .build()
    }

    suspend fun updateTime(time: String) = context.dataStore.updateData { preferences ->
        preferences.toBuilder()
            .setTime(time)
            .build()
    }

    suspend fun updateRate(rate: Double) { context.dataStore.updateData { preferences ->
        preferences.toBuilder().setRate(rate).build()
    }
    }

    suspend fun clearTime() { context.dataStore.updateData { preferences ->
        preferences.toBuilder().clearTime().build()
    }
    }


    suspend fun clearRate() { context.dataStore.updateData { preferences ->
        preferences.toBuilder().clearRate().build()
    }
    }

    suspend fun clearDataStore() { context.dataStore.updateData { preferences ->
        preferences.toBuilder().clear().build()
    }
    }

}