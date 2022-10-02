package com.dev_marinov.cryptocash.domain.dataStore

import com.dev_marinov.cryptocash.data.Date
import com.dev_marinov.cryptocash.data.Rate
import kotlinx.coroutines.flow.Flow

interface IProtoDataStoreRepository {

    suspend fun saveData(date: Date?)
    suspend fun getData() : Flow<Date?>
    suspend fun saveRate(rate: Rate?)
    suspend fun getRate() : Flow<Rate?>
    suspend fun deleteData()
}