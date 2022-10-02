package com.dev_marinov.cryptocash.presentation.showCurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dev_marinov.cryptocash.data.Date
import com.dev_marinov.cryptocash.data.Rate
//import com.dev_marinov.cryptocash.data.dataStore.DataStoreRepository
import com.dev_marinov.cryptocash.data.local.ProtoDataStoreRepository

import com.dev_marinov.cryptocash.domain.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ShowCurrencyViewModel @Inject constructor(
    private val protoDataStoreRepository: ProtoDataStoreRepository
) : ViewModel() {

    val dateTime = protoDataStoreRepository.dataPreferencesFlow.asLiveData()

    fun updateRate(rate: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            protoDataStoreRepository.updateRate(rate = rate)
        }
    }

    var countRate = 1.1

    private fun getFAKERateApiCycle(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                //saveRate(countRate)
                updateRate(countRate)
                delay(1000L)
                countRate += 0.1
            }
        }
    }

    fun start(){
        getFAKERateApiCycle().start()
    }
    fun cancel(){
        getFAKERateApiCycle().cancel()
    }
}