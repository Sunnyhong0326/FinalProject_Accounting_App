package com.codelab.basiclayouts.feature_account.presentation.graph

import android.os.Build
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.HexToJetpackColor
import com.codelab.basiclayouts.feature_account.domain.model.AccountTypeTotalData
import com.codelab.basiclayouts.feature_account.presentation.record_list.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases
) : ViewModel(){

    private var _graphInterval by mutableStateOf("Year")
    val graphInterval: String
        get() = _graphInterval

    private var _incomeTypePercentageData by mutableStateOf(listOf<AccountTypeTotalData>())
    val incomeTypePercentageData: List<AccountTypeTotalData>
        get() = _incomeTypePercentageData

    private var _incomeMoneyData = getIncomeMoney().toMutableStateList()
    val incomeMoneyData: List<Float>
        get() = _incomeMoneyData

    private var _expenseTypePercentageData by mutableStateOf(listOf<AccountTypeTotalData>())
    val expenseTypePercentageData: List<AccountTypeTotalData>
        get() = _expenseTypePercentageData

    private var _expenseMoneyData = getExpenseMoney().toMutableStateList()
    val expenseMoneyData: List<Float>
        get() = _expenseMoneyData

    private fun getIncomeMoney(): List<Float> {
        val tmpList = mutableListOf<Float>()

        for(i in 0..(_incomeTypePercentageData.size-1)){
            tmpList.add(_incomeTypePercentageData[i].money.toFloat())
        }

        return tmpList.toList()
    }

    private fun getExpenseMoney(): List<Float> {
        val tmpList = mutableListOf<Float>()

        for(i in 0..(_expenseTypePercentageData.size-1)){
            tmpList.add(_expenseTypePercentageData[i].money.toFloat())
        }

        return tmpList.toList()
    }

    // 這一個function用來配合 toggle button，選擇graph要顯示一年、一個月還是一天
    // return 有三種可能 Year Month Day
    fun setGraphInterval(interval: String){
        _graphInterval = interval
    }

    val currentDate = getCurrentDate()
    val date = currentDate.year.plus("/")
        .plus(currentDate.month)
        .plus("/")
        .plus(currentDate.day)

    fun getExpenseDataFromDatabase(){
        viewModelScope.launch {
            _expenseTypePercentageData = accountingUseCases.getRecordByTimeInterval(true, date, _graphInterval).toMutableStateList()
            _expenseMoneyData = getExpenseMoney().toMutableStateList()
        }
    }
    fun getIncomeDataFromDatabase(){
        viewModelScope.launch {
            _incomeTypePercentageData = accountingUseCases.getRecordByTimeInterval(false, date, _graphInterval).toMutableStateList()
            _incomeMoneyData = getIncomeMoney().toMutableStateList()
        }
    }
//    fun initalInterval(){
//        _graphInterval = "Year"
//    }

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

//private fun getIncomeData(): List<AccountTypeTotalData> {
//    return listOf(
//        AccountTypeTotalData("打工", 0.3f, HexToJetpackColor.getColor("FF9898"), 300),
//        AccountTypeTotalData("薪水", 0.2f, HexToJetpackColor.getColor("5388D8"), 200),
//        AccountTypeTotalData("獎金", 0.15f, HexToJetpackColor.getColor("F4BE37"), 150),
//        AccountTypeTotalData("股票", 0.15f, HexToJetpackColor.getColor("FAA73C"), 150),
//        AccountTypeTotalData("投資", 0.1f, HexToJetpackColor.getColor("FF9040"), 100),
//        AccountTypeTotalData("租金", 0.1f, HexToJetpackColor.getColor("00AF54"), 100),
//    )
//}
//
//private fun getExpenseData(): List<AccountTypeTotalData> {
//    return listOf(
//        AccountTypeTotalData("飲食", 0.3f, HexToJetpackColor.getColor("7A96EB"), 300),
//        AccountTypeTotalData("娛樂", 0.2f, HexToJetpackColor.getColor("B9E2FC"), 200),
//        AccountTypeTotalData("學習", 0.15f, HexToJetpackColor.getColor("F9DA8B"), 150),
//        AccountTypeTotalData("交通", 0.15f, HexToJetpackColor.getColor("6CD534"), 150),
//        AccountTypeTotalData("用品", 0.1f, HexToJetpackColor.getColor("79EA7A"), 100),
//        AccountTypeTotalData("醫療", 0.1f, HexToJetpackColor.getColor("85FFC0"), 100),
//    )
//}
