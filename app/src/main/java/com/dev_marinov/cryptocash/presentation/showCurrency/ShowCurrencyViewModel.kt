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
    //private val iRepository: IRepository,
    private val protoDataStoreRepository: ProtoDataStoreRepository
) : ViewModel() {

    val dateTime = protoDataStoreRepository.dataPreferencesFlow.asLiveData()

    init {
        //getFAKERateApiCycle()
    }


    fun updateRate(rate: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            protoDataStoreRepository.updateRate(rate = rate)
        }
    }

    var countRate = 1.1

    init {
        //getDate()
        //getFAKERateApiCycle().start()
    }

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













    //////////////////////
    // поля


//    val dateTime: Flow<Date> = flow {
//        //getEshe()
//        getDateDataStore()
//    }



//    suspend fun getDateDataStore() : Flow<Date> {
//        return dataStoreRepository.getData()
//    }

//    var dateTime: Flow<Date> = flow {
//        suspend {
//            dataStoreRepository.getData().first()
//        }
//    }

//    var dateTime: Flow<Date> = dataStoreRepository.dateTime
//    var rate: Flow<Rate?> = dataStoreRepository.rate
    ////////////////////



    fun getDate() {
//        Log.d("333", " загр getDate()")
//        // аналог коллект
//        dateTime.onEach {
//            if (it.datePicker != "null" && it.timePicker != "null") {
//                getFAKERateApiCycle().cancel()
//            } else {
//                getFAKERateApiCycle().start()
//            }
//            // пока жив viewmodel
//        }.launchIn(viewModelScope)
    }

//    private fun saveRate(usd: Double) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStoreRepository.saveRate(Rate(rateEthereum = usd))
//        }
//    }
    // как бы правильный вариант
//    private fun getRateApiCycle(): Job {
//        return viewModelScope.launch(Dispatchers.IO) {
//            while (isActive) {
//                iRepository.getUSD()?.let {
//                    //_rate.value = it.usd
//                    saveRate(it.usd)
//                    delay(1000)
//                }
//            }
//        }
//    }
}