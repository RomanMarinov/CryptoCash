package com.dev_marinov.cryptocash.presentation.bottomSheet

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.cryptocash.R
import com.dev_marinov.cryptocash.SingleLiveEvent
import com.dev_marinov.cryptocash.data.ConvertDate
import com.dev_marinov.cryptocash.data.local.ProtoDataStoreRepository

import com.dev_marinov.cryptocash.domain.DateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    //private val dataStoreRepository: DataStoreRepository,
    private val protoDataStoreRepository: ProtoDataStoreRepository
    ) :
    ViewModel() {

    private val dateConverter = DateConverter()

    //val datetime: Flow<Date> = dataStoreRepository.dateTime
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


    //////////////////////////////////
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
        //startRetrieveData()
    }

    fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
            _date.value?.let { protoDataStoreRepository.updateDate(it) }
            _time.value?.let { protoDataStoreRepository.updateTime(it) }
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

    fun retrieveData() {
//        datetime.onEach {
//            if (it.datePicker != "null" && it.timePicker != "null") {
//                _date.postValue(it.datePicker)
//                _time.postValue(it.timePicker)
//                _buttonDeleteBottomSheet.postValue(true)
//            } else {
//                _buttonDeleteBottomSheet.postValue(false)
//            }
//        }
    }

    fun deleteDateTimeDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            //dataStoreRepository.deleteData()
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

//    private fun startRetrieveData() {
//        if (_date.value == null && _time.value == null) retrieveData()
//    }

}