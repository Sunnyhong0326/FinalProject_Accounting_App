package com.codelab.basiclayouts.feature_account.presentation.homepage

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.core.domain.use_case.GraphQLUseCases
import com.codelab.basiclayouts.feature_account.domain.model.FriendInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases,
    private val graphQLUseCases: GraphQLUseCases
) : ViewModel() {
    private var _friendList = getFriendData().toMutableStateList()
    //var friendsList = mutableListOf<FriendInfo>()
    //private val state = MutableStateOf(HomeState)
    init {
        getAllFriends()
    }
    val friendList: List<FriendInfo>
        get() = _friendList

    // 下面這是展示用 到時候要移到初始頁面initialize卡片
    fun addCards(){
        viewModelScope.launch {
            accountingUseCases.cardInit()
        }
    }

    fun searchUser(text: String){
        viewModelScope.launch(Dispatchers.IO) {
            graphQLUseCases.getUserByName(userName = text)
            _friendList = friendsList.toMutableStateList()
        }
    }

    fun getAllFriends(){
        friendsList = mutableListOf()
        viewModelScope.launch(Dispatchers.IO) {
            friendsList = graphQLUseCases.getFriendList(userId = "1")
            _friendList = friendsList.toMutableStateList()
        }
    }
}
/*
data class HomeState(

)*/
private fun getFriendData(): List<FriendInfo>{
    return friendsList
}
