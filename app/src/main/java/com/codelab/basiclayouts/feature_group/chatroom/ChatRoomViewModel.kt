package com.codelab.basiclayouts.feature_group.chatroom

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.core.domain.use_case.GraphQLUseCases
import com.codelab.basiclayouts.feature_group.MessageData
import com.codelab.basiclayouts.feature_group.messageList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases,
    private val graphQLUseCases: GraphQLUseCases
) : ViewModel() {
    private val _groupMessages = getMessageHistoryData().toMutableStateList()
    val groupMessages: List<MessageData>
        get() = _groupMessages

    fun sendMessage(inputValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            graphQLUseCases.sendMessage(
                userId = accountingUseCases.getUserAccount(),
                roomId = "2",
                message = inputValue
            )
        }
    }
}

private fun getMessageHistoryData(): List<MessageData>{
    return messageList
}

