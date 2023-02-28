package com.codelab.basiclayouts.core.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.codelab.basiclayouts.GetAllFriendsQuery
import com.codelab.basiclayouts.GetChatRoomMessagesQuery
import com.codelab.basiclayouts.GetUserChatRoomQuery
import com.codelab.basiclayouts.RealTimeMessagesSubscription
import kotlinx.coroutines.flow.Flow

interface GraphqlRepo {

    suspend fun createRoom(roomName: String): String

    suspend fun createUser(userName: String): String

    suspend fun inviteFriends(userId: String, groupId: String): String

    suspend fun sendMessage(userId: String, groupId: String, message: String): String

    suspend fun getUserChatRoom(userId: String): List<GetUserChatRoomQuery.GetUserChatRoom?>?

    suspend fun getRoomMessages(roomId: String): List<GetChatRoomMessagesQuery.GetChatRoomMessage?>?

    suspend fun getRealTimeMessage(roomId: String): Flow<ApolloResponse<RealTimeMessagesSubscription.Data>>?

    suspend fun getFriendList(userId: String): List<GetAllFriendsQuery.GetAllFriend?>?

    suspend fun getUserById(userId: String) : String?

    suspend fun getChatRoomById(roomId: String): String?

    suspend fun getUserByName(userName: String): String?
}