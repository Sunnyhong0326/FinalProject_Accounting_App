package com.codelab.basiclayouts.feature_account.presentation.record_list

import android.os.Build
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.feature_account.domain.model.AccountRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases
) : ViewModel() {
    //private var _recordData = getRecordData().toMutableStateList()
    private var _recordData by mutableStateOf(listOf<AccountRecord>())

    private var _currentDate by mutableStateOf(getCurrentDate())


    val recordData: List<AccountRecord>
        get() = _recordData

    val currentDate: Date
        get() = _currentDate

    fun updateCurrentDate(year: String, month: String, day: String){
        _currentDate.year = year
        _currentDate.month = month
        _currentDate.day = day
    }

    fun getRecordDataFromDatabase(date: String){
        viewModelScope.launch {
            _recordData = accountingUseCases.getRecordByDate(date).toMutableStateList()
        }
    }

    fun goIntoRecordPage(){
        val date = currentDate.year.plus("/")
            .plus(currentDate.month)
            .plus("/")
            .plus(currentDate.day)
        _currentDate = getCurrentDate()
        getRecordDataFromDatabase(date)
    }

    init {
        val date = currentDate.year.plus("/")
            .plus(currentDate.month)
            .plus("/")
            .plus(currentDate.day)
        getRecordDataFromDatabase(date)
    }
}

private fun getCurrentDate(): Date {
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()

    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)
    return Date(
        formatted.substring(0, 4),
        formatted.substring(5, 7),
        formatted.substring(8, 10)
    )
}

data class Date(
    var year: String = "2022",
    var month: String = "06",
    var day: String = "17"
)