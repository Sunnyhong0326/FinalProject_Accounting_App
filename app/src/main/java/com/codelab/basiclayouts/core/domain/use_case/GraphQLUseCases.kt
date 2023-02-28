package com.codelab.basiclayouts.core.domain.use_case

import android.os.Message
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.codelab.basiclayouts.GetChatRoomMessagesQuery
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.RealTimeMessagesSubscription
import com.codelab.basiclayouts.core.domain.repository.GraphqlRepo
import com.codelab.basiclayouts.feature_account.domain.model.FriendInfo
import com.codelab.basiclayouts.feature_group.MessageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GraphQLUseCases (
    private val graphqlRepo : GraphqlRepo
){
    // 回傳朋友列表 List<Friend> list[index].userId list[index].userName
    suspend fun getFriendList(userId: String): MutableList<FriendInfo> {
        var friendIdList : MutableList<String?> = mutableListOf<String?>()
        val friendList: MutableList<FriendInfo> = mutableListOf<FriendInfo>()
        graphqlRepo.getFriendList(userId = userId)?.forEach{ element ->
            if (element != null) {
                friendIdList.add(element.friendId)
            }
        }
        for (id in friendIdList){
            friendList.add(
                FriendInfo(
                    name = id?.let { graphqlRepo.getUserById(userId = it) }.toString(),
                    intimacy = 0,
                    avatar = R.drawable.friend_2
                )
            )
        }
        return friendList
    }

    suspend fun sendMessage(userId: String, roomId: String, message: String){
        graphqlRepo.sendMessage(userId = userId, groupId = roomId, message = message)
    }

    suspend fun getUserChatRoom(userId: String): List<ChatRoom>{
        var chatRoomIdList: MutableList<String?> = mutableListOf<String?>()
        val chatRoomList: MutableList<ChatRoom> = mutableListOf<ChatRoom>()
        graphqlRepo.getUserChatRoom(userId = userId)?.forEach{element->
            if (element != null) {
                Log.d("GraphQL", "element: ${element.roomId}")
                chatRoomIdList.add(element.roomId)
            }
        }
        for (id in chatRoomIdList){
            Log.d("GraphQL", "id in list $id")
            chatRoomList.add(ChatRoom(
                roomId = id,
                roomName = id?.let { graphqlRepo.getChatRoomById(roomId = it) }
                )
            )
        }
        return chatRoomList
    }


    suspend fun getRoomMessages(roomId: String): List<GetChatRoomMessagesQuery.GetChatRoomMessage?>?{
        return graphqlRepo.getRoomMessages(roomId = roomId)
    }

    suspend fun getRealTimeMessage(roomId: String): Flow<ApolloResponse<RealTimeMessagesSubscription.Data>>?{
        return graphqlRepo.getRealTimeMessage(roomId = roomId)
    }

    suspend fun createGroup(roomName: String): String? {
        return graphqlRepo.createRoom(roomName = roomName)
    }

    suspend fun inviteFriends(roomId : String, userId: String): String{
        return graphqlRepo.inviteFriends(groupId = roomId, userId = userId)
    }

    suspend fun getUserById(userId: String): String? {
        return graphqlRepo.getUserById(userId = userId)
    }

    suspend fun getUserByName(userName: String): String? {
        return graphqlRepo.getUserByName(userName = userName)
    }
}
data class ChatRoom(
    val roomId: String?,
    val roomName: String?
)
data class Friend(
    val userId: String?,
    val userName: String?
)