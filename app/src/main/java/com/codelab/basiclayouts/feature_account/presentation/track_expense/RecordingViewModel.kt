package com.codelab.basiclayouts.feature_account.presentation.track_expense

import android.os.Build
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.feature_account.domain.model.TrackInfo
import com.codelab.basiclayouts.feature_account.presentation.record_list.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases
) : ViewModel() {

    private var _category by mutableStateOf("Expense")
    val category: String
        get() = _category

    fun changeCategory(category: String){
        _category = category
        if(category == "Expense") _typeChoose = "飲食"
        else _typeChoose = "打工"
    }

    private val _expenseType = getExpenseType().toMutableStateList()
    val expenseType: List<String>
        get() = _expenseType

    private val _incomeType = getIncomeType().toMutableStateList()
    val incomeType: List<String>
        get() = _incomeType

    private var _typeChoose by mutableStateOf("飲食")
    val typeChoose: String
        get() = _typeChoose

    fun typeChange(type: String) {
        _typeChoose = type
    }

    var _currentDate by mutableStateOf(getCurrentDate())
    val currentDate: Date
        get() = _currentDate

    var currentDateStandard = _currentDate.year.plus("/")
        .plus(currentDate.month)
        .plus("/")
        .plus(currentDate.day)

    private var _recordDate by mutableStateOf(currentDateStandard)
    val recordDate: String
        get() = _recordDate

    fun dateChange(date: String) {
        _recordDate = date
    }

    private var _item by mutableStateOf("")
    val item: String
        get() = _item

    fun itemChange(item: String) {
        _item = item
    }

    private var _price by mutableStateOf(0)
    val price: Int
        get() = _price

    fun priceChange(price: Int) {
        _price = price
    }

    fun sendToDatabase(){
        viewModelScope.launch {
            accountingUseCases.addRecord(
                TrackInfo(
                    _recordDate, _item, _price, _typeChoose),
                _category == "Expense"
            )
        }
    }
}

private fun getExpenseType(): List<String> {
    return listOf("飲食","娛樂","學習","交通","用品","醫療")
}

private fun getIncomeType(): List<String> {
    return listOf("打工","薪水","獎金","股票","投資","租金")
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
