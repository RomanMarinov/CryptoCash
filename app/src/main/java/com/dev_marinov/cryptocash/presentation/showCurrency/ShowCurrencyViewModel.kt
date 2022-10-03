package com.dev_marinov.cryptocash.presentation.showCurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dev_marinov.cryptocash.data.datetime.DateTimeRepository
import com.dev_marinov.cryptocash.domain.IRateRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ShowCurrencyViewModel @Inject constructor(
    private val iRateRepository: IRateRepository,
    private val dataStoreRepository: DateTimeRepository
) : ViewModel() {

    val dateTime = dataStoreRepository.dateTime.asLiveData()
    val rateStore = dataStoreRepository.rate.asLiveData()

    private var job: Job? = null

    private val _rate: MutableLiveData<Double> = MutableLiveData()
    val rate: LiveData<Double> = _rate

    private fun getRate() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                iRateRepository.getUSD()?.let {
                    _rate.postValue(it.usd)
                    delay(1000L)
                }
            }
        }
    }

    fun cancelJobSaveRate() {
        job?.cancel()
        _rate.value?.let { saveRate(it) }
    }

    fun startJob() {
        getRate()
    }

    private fun saveRate(rate: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveRate(rate = rate)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun onStop() {
        job?.cancel()
    }
}
