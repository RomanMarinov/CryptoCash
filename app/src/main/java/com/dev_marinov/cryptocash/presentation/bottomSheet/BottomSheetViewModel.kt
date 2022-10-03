package com.dev_marinov.cryptocash.presentation.bottomSheet

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.dev_marinov.cryptocash.R
import com.dev_marinov.cryptocash.SingleLiveEvent
import com.dev_marinov.cryptocash.data.datetime.ConvertDate
import com.dev_marinov.cryptocash.data.datetime.DateTimeRepository

import com.dev_marinov.cryptocash.domain.DateConverter
import com.dev_marinov.cryptocash.domain.DateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val dataStoreRepository: DateTimeRepository,
) :
    ViewModel() {

    private val dateConverter = DateConverter()

    private val _date: MutableLiveData<String> = MutableLiveData()
    private val _time: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> = _date
    val time: LiveData<String> = _time

    private val _curDateName: MutableLiveData<String> = MutableLiveData()
    var curDateName: LiveData<String> = _curDateName
    private val _prevDateName: MutableLiveData<String> = MutableLiveData()
    var prevDateName: LiveData<String> = _prevDateName
    private val _curDateTime: MutableLiveData<String> = MutableLiveData()
    var curDateTime: LiveData<String> = _curDateTime

    private val _uploadedData = SingleLiveEvent<String>()
    val uploadedData: SingleLiveEvent<String> = _uploadedData

    private val _buttonOkBottomSheet: MutableLiveData<Boolean> = MutableLiveData()
    var buttonOkBottomSheet: LiveData<Boolean> = _buttonOkBottomSheet
    private val _buttonDeleteBottomSheet: MutableLiveData<Boolean> = MutableLiveData()
    var buttonDeleteBottomSheet: LiveData<Boolean> = _buttonDeleteBottomSheet

    init {
        setButtonOkBottomSheet()
        _curDateName.postValue(getCurrentDayName())
        _prevDateName.postValue(getPreviousDayName())
        _curDateTime.postValue(getCurrentDateTime())
    }

    fun saveDateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            _date.value?.let { date ->
                _time.value?.let { time ->
                    dataStoreRepository.saveDateTime(DateTime(date, time))
                }
            }
        }
        getToast()
    }

    private fun getToast() {
        _uploadedData.value = R.string.copied_to_clipboard.toString()
    }

    fun onDateSelected(year: Int?, month: Int?, day: Int?) {
        val objectConvertDate = ConvertDate(year = year, month = month, day = day)
        _date.value = dateConverter.getName(convertDate = objectConvertDate)
        setButtonOkBottomSheet()
        setButtonDeleteBottomSheet()
    }

    fun onTimeSelected(hour: Int?, minute: Int?) {
        _time.value = "$hour:$minute"
        setButtonOkBottomSheet()
        setButtonDeleteBottomSheet()
    }

    fun setButtonOkBottomSheet() {
        _buttonOkBottomSheet.value = _date.value != null && _time.value != null
    }

    fun setButtonDeleteBottomSheet() {
        _buttonDeleteBottomSheet.value =
            _date.value != null || _time.value != null || _date.value != null && _time.value != null
    }


    fun deleteDateTimeDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.clear()
        }
    }

    fun deleteDataTimeLiveData() {
        _date.value = null
        _time.value = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPreviousDayName(): String {
        val previousDay = LocalDate.now().dayOfWeek - 1
        return previousDay.name
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDayName(): String {
        return LocalDate.now().dayOfWeek.name
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        return sdf.format(Date())
    }

}