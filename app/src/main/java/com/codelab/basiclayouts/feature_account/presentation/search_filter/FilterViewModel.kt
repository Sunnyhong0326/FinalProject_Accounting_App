package com.codelab.basiclayouts.feature_account.presentation.search_filter

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.feature_account.domain.model.AccountRecord
import com.codelab.basiclayouts.feature_account.domain.model.FilterOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases
) : ViewModel() {
    //private var _recordData = getRecordData().toMutableStateList()
    private var _recordData by mutableStateOf(listOf<AccountRecord>())

    private var _searchKey by mutableStateOf("")

    private var _filterOption by mutableStateOf(FilterOption())

    val recordData: List<AccountRecord>
        get() = _recordData

    val searchKey: String
        get() = _searchKey

    val filterOption: FilterOption
        get() = _filterOption

    fun setFilterOption(
        setTarget: String,
        sortBy: String = "",
        interests: List<String> = listOf(),
        priceLow: Int? = 0,
        priceHigh: Int? = 10000,
    ){
        when (setTarget) {
            "sortBy" -> {
                _filterOption.sortBy = sortBy
            }
            "interests" -> {
                _filterOption.interests = interests
            }
            "priceLow" -> {
                if(priceLow==null)
                    _filterOption.priceLow = 0
                else
                    _filterOption.priceLow = priceLow
            }
            "priceHigh" -> {
                if(priceHigh==null)
                    _filterOption.priceHigh = 10000
                else
                    _filterOption.priceHigh = priceHigh
            }
        }
    }

    fun setSearchKey(text: String) {
        _searchKey = text
    }

    fun getFilterDataByName() {
        viewModelScope.launch {
            _recordData = accountingUseCases.getRecordByName(_searchKey).toMutableStateList()
        }
    }
    fun getFilterDataByOption() {
        viewModelScope.launch {
            _recordData = accountingUseCases.getRecordByOption(_filterOption).toMutableStateList()
        }
    }
    fun clearNameFilter() {
        _searchKey = ""
    }
    fun clearOptionFilter() {
        _filterOption = FilterOption()
    }
}



//private fun getRecordData(): List<AccountRecord>{
//    return listOf(
//        AccountRecord(
//            // AccountType.FOOD,
//            "Bubble Tea",
//            50,
//            "2022/6/4/10:52",
//            "飲食"
//        ),
//        AccountRecord(
//            // AccountType.LOTTERY,
//            "Lottery",
//            1000,
//            "2022/6/4/11:33",
//            "獎金"
//        ),
//        AccountRecord(
//            // AccountType.FOOD,
//            "Pizza",
//            200,
//            "2022/6/4/20:19",
//            "飲食"
//        ),
//        AccountRecord(
//            // AccountType.CLOTHS,
//            "T-shirt",
//            500,
//            "2022/6/4/21:29",
//            "服裝"
//        ),
//        AccountRecord(
//            // AccountType.EDUCATION,
//            "Book_A",
//            3000,
//            "2022/6/4/23:12",
//            "教育"
//        ),
//    )
//}