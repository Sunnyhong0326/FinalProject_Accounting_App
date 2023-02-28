package com.codelab.basiclayouts.feature_group.group_create

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.core.domain.use_case.GraphQLUseCases
import com.codelab.basiclayouts.feature_group.MonsterData
import com.codelab.basiclayouts.type.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases,
    private val graphQLUseCases: GraphQLUseCases
) : ViewModel() {
    var selectedFriends: MutableList<UserData> = mutableListOf()
    var groupName: String by mutableStateOf("")
    var setMaximum: String by mutableStateOf("")
    var createdroomId = ""
    private var _searchKey by mutableStateOf("")
    val searchKey: String
        get() = _searchKey

    private var _friendDataList = getFriendList().toMutableStateList()
    val friendDataList: List<UserData>
        get() = _friendDataList

    init {
        getAllFriends()
    }

    fun addFriend(userData: UserData){

    }

    fun getAllFriends(){
        viewModelScope.launch(Dispatchers.IO) {
            val tmp : MutableList<UserData> = mutableListOf()
            graphQLUseCases.getFriendList(userId = "1").forEach{
                tmp.add(UserData(
                    name = it.name.toString(),
                    profile = R.drawable.friend_2,
                    completedTasksNum = 1,
                    ownedMonsters = ownedMonsterList
                ))
            }
            _friendDataList = tmp.toMutableStateList()
        }
    }
    fun invite(){
        viewModelScope.launch(Dispatchers.IO) {
            graphQLUseCases.inviteFriends(userId = "1", roomId = createdroomId)
            selectedFriends.forEach{
                val id = graphQLUseCases.getUserByName(userName = it.name).toString()
                graphQLUseCases.inviteFriends(userId = id, roomId = createdroomId)
            }
        }
    }
    fun select(userData: UserData, selectState: Boolean){
        if(selectState) selectedFriends.add(userData)
        else selectedFriends.remove(userData)
    }
    fun createGroup() {
        viewModelScope.launch(Dispatchers.IO){
            createdroomId = graphQLUseCases.createGroup(roomName = groupName).toString()
            invite()
        }
    }
    fun setSearchKey(text: String) {
        _searchKey = text
    }
}

private fun getFriendList(): List<UserData>{
    return friendsList
}

@Parcelize
data class UserData(
    @DrawableRes val profile: Int,
    val name: String,
    val completedTasksNum: Int,
    val ownedMonsters: List<MonsterData>
): Parcelable

class UserDataType : NavType<UserData>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UserData? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): UserData {
        return Gson().fromJson(value, UserData::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: UserData) {
        bundle.putParcelable(key, value)
    }
}

val ownedMonsterList = listOf(
    MonsterData(R.drawable.monster_1, "monster_1"),
    MonsterData(R.drawable.monster_2, "monster_2"),
    MonsterData(R.drawable.monster_3, "monster_3"),
    MonsterData(R.drawable.monster_4, "monster_4"),
    MonsterData(R.drawable.monster_5, "monster_5")
)

var friendsList = mutableListOf<UserData>()
/*    UserData(
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
)*/