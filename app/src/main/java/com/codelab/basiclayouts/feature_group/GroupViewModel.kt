package com.codelab.basiclayouts.feature_group

import android.os.Parcelable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.core.domain.use_case.GraphQLUseCases
import com.codelab.basiclayouts.feature_group.group_create.UserData
import com.codelab.basiclayouts.feature_group.group_create.ownedMonsterList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases,
    private val graphQLUseCases: GraphQLUseCases
) : ViewModel() {
    var hatchProgress: Float by mutableStateOf(0f)
    var readyToHatch: Boolean by mutableStateOf(false)

    private var _state = mutableStateOf(ChatState())
    val state = _state

    //private val _groupNames : MutableList<String?> = mutableListOf()
    //val groupNames: List<String?> = _groupNames

    //private val _groupIds: MutableList<String?> = mutableListOf()
    //val groupIds: List<String?> = _groupIds

    //private var currentRoomId by mutableStateOf("")
    //private var _currentRoomName by mutableStateOf("")
    //val currentRoomName = _currentRoomName
    //private val _groupsList = getGroupsList().toMutableStateList()
    //val groupList: List<String?>
    //    get() = _groupsList
    //private var _groupMessages : MutableList<MessageData> = mutableStateListOf()
    //val groupMessages: List<MessageData>
    //    get() = _groupMessages

    private val _memberDataList = getMemberList().toMutableStateList()
    val memberDataList: List<UserData>
        get() = _memberDataList

    private var _newHatchedMonster = getMyCollection().toMutableStateList()
    val newHatchedMonster: MonsterData
        get() = _newHatchedMonster.last()

    private val _monstersInPark = getMonstersInPark().toMutableStateList()
    val monstersInPark: List<MonsterData>
        get() = _monstersInPark

    private val _groupTaskList = getGroupTaskList().toMutableStateList()
    val groupTaskList: List<TaskData>
        get() = _groupTaskList

    init {
        Log.d("GraphQL", "initialize")
        getAllGroups()
    }

    //private fun getGroupsList(): List<String?> {
    //    return _groupNames
    //}

    fun getAllGroups(){
        _state.value = _state.value.copy(
            userChatRoomNames = mutableListOf(),
            userChatRoomIds = mutableListOf()
        )
        viewModelScope.launch(Dispatchers.IO){
            graphQLUseCases.getUserChatRoom(
                userId = "1"
            ).forEach{
                _state.value = _state.value.copy(
                    userChatRoomIds =  _state.value.userChatRoomIds+ it.roomId,
                    userChatRoomNames = _state.value.userChatRoomNames + it.roomName
                )
                Log.d("GraphQL", "Add to groupNames ${it.roomName}")
            }
        }
    }
    fun setCurrentRoomIdAndName(index: Int){
        Log.d("GraphQL", "index : $index  name: ${_state.value.userChatRoomNames[index].toString()}")
        _state.value = _state.value.copy(
            currentRoomId = _state.value.userChatRoomIds[index].toString(),
            currentRoomName = _state.value.userChatRoomNames[index].toString()
        )
    }

    fun sendMessage(inputValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            graphQLUseCases.sendMessage(
                userId = "1",
                roomId = state.value.currentRoomId,
                message = inputValue
            )
        }
    }

    fun listenToMessages(){
        viewModelScope.launch(Dispatchers.IO) {
            graphQLUseCases.getRoomMessages(roomId = _state.value.currentRoomId)?.forEach { it->
                if (it != null) {
                    _state.value = _state.value.copy(
                        chatRoomMessages = _state.value.chatRoomMessages + MessageData(
                            userId = it.userId,
                            message = it.message,
                            profile = R.drawable.friend_1
                        )
                    )
                }
            }
            graphQLUseCases.getRealTimeMessage(roomId = _state.value.currentRoomId)?.collect { it ->
                _state.value = _state.value.copy(
                    chatRoomMessages = _state.value.chatRoomMessages + MessageData(
                        userId = it.data?.realTimeMessages?.userId,
                        message = it.data?.realTimeMessages?.message,
                        profile = R.drawable.friend_1
                    )
                )
            }
        }
    }

    fun remind(userData: UserData){}

    fun hatch(){}

    //點入任務頁面時更新
    fun updateTasks(){}

    //進入群組時更新, readyToHatch也要更新
    fun updateHatchProgress(){}
}

private fun getMyCollection(): List<MonsterData> {
    return ownedMonsterList
}

private fun getMonstersInPark(): List<MonsterData> {
    return monstersInParkList
}

private fun getGroupTaskList(): List<TaskData> {
    return taskList
}

private fun getMemberList(): List<UserData>{
    return memberDataList
}

data class ChatState(
    var chatRoomMessages: List<MessageData> =  mutableListOf<MessageData>(),
    var currentRoomId: String = "",
    var currentRoomName: String = "",
    var userChatRoomIds: List<String?> = mutableListOf<String>(),
    var userChatRoomNames: List<String?> = mutableListOf<String>()
)

@Parcelize
data class MonsterData(
    @DrawableRes val picture: Int,
    val name: String
): Parcelable

val monstersInParkList = listOf<MonsterData>(
    MonsterData(R.drawable.monster_1, "monster_1"),
    MonsterData(R.drawable.monster_2, "monster_2"),
    MonsterData(R.drawable.monster_3, "monster_3"),
    MonsterData(R.drawable.monster_4, "monster_4")
)

data class TaskData(
    val description: String,
    val award: Float,
    val progress: Float
)

val taskList = listOf(
    TaskData("所有群組成員連續記帳三天", 0.1f, 0.75f),
    TaskData("吃早餐了沒? 集滿10次早餐紀錄", 0.15f, 0.5f),
    TaskData("有錢人的煩惱 - 集滿3筆上千元消費", 0.1f, 0.8f),
    TaskData("努力又孝順的孩子 - 集滿5筆打工紀錄", 0.1f, 0.15f),
    TaskData("記帳鬧鐘 - 累計100次記帳提醒", 0.05f, 0.9f)
)

val memberDataList = listOf(
    UserData(
        profile = R.drawable.member_1,
        name = "Wu Shang Hong",
        completedTasksNum = 56,
        ownedMonsters = ownedMonsterList
    ),
    UserData(
        profile = R.drawable.member_2,
        name = "Sophia",
        completedTasksNum = 40,
        ownedMonsters = ownedMonsterList
    ),
    UserData(
        profile = R.drawable.member_3,
        name = "Lucy",
        completedTasksNum = 31,
        ownedMonsters = ownedMonsterList
    ),
    UserData(
        profile = R.drawable.member_4,
        name = "Alice",
        completedTasksNum = 23,
        ownedMonsters = ownedMonsterList
    ),
    UserData(
        profile = R.drawable.member_5,
        name = "Christen",
        completedTasksNum = 12,
        ownedMonsters = ownedMonsterList
    )
)

data class MessageData(
    @DrawableRes val profile: Int,
    val userId: String?,
    val message: String?
)

val messageList = listOf(
    MessageData(R.drawable.friend_1, "1", "There are 5 tasks remaining."),
    MessageData(R.drawable.member_1, "2", "Let's go!"),
    MessageData(R.drawable.friend_2, "3", "You forgot to record your breakfast")
)

val groupNames = listOf(
    "GroupA", "GroupB", "GroupC", "GroupD"
)