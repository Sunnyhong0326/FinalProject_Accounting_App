package com.codelab.basiclayouts.core.data.repository

import android.util.Log
import com.apollographql.apollo3.api.ApolloResponse
import com.codelab.basiclayouts.*
import com.codelab.basiclayouts.core.data.apolloClient
import com.codelab.basiclayouts.core.domain.repository.GraphqlRepo
import kotlinx.coroutines.flow.Flow

const val TAG = "GraphQL"
class GraphqlRepoImp : GraphqlRepo {

    override suspend fun createRoom(roomName: String): String {
        Log.d(TAG, "createRoom: $roomName")
        val response = try {
            apolloClient().mutation(CreateRoomMutation(roomName = roomName)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.createRoom.toString()
    }

    override suspend fun createUser(userName: String): String {
        Log.d(TAG, "createUser: $userName")
        val response = try {
            apolloClient().mutation(CreateUserMutation(userName = userName)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.createUser.toString()
    }

    override suspend fun inviteFriends(userId: String, groupId: String): String {
        Log.d(TAG, "inviteFriends: UserId: $userId, RoomId: $groupId")
        val response = try {
            apolloClient().mutation(
                AddMemberToRoomMutation(
                    userId = userId,
                    roomId = groupId
                )
            ).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.addMemberToRoom.toString()
    }

    override suspend fun sendMessage(userId: String, groupId: String, message: String): String {
        Log.d(TAG, "UserId $userId sends $message to Group $groupId")
        val response = try {
            apolloClient().mutation(
                SendMessageMutation(
                    userId = userId,
                    roomId = groupId,
                    text = message
                )
            ).execute()
        } catch (e: Exception) {
            System.out.println("Error " + e.message)
            null
        }
        return response?.data?.sendMessage.toString()
    }

    override suspend fun getUserChatRoom(userId: String): List<GetUserChatRoomQuery.GetUserChatRoom?>?
    {
        Log.d(TAG, "Get UserId: $userId ChatRoom")
        val response = try {
            apolloClient().query(
                GetUserChatRoomQuery(
                    userId = userId
                )).execute()
        } catch (e: Exception) {
            System.out.println("Error " + e.message)
            null
        }
        return response?.data?.getUserChatRoom
    }

    override suspend fun getRoomMessages(roomId: String): List<GetChatRoomMessagesQuery.GetChatRoomMessage?>? {
        Log.d(TAG, "createUser: $roomId")
        val response = try {
            apolloClient().query(GetChatRoomMessagesQuery(roomId = roomId)).execute()
        } catch (e: Exception) {
            System.out.println("Error " + e.message)
            null
        }
        return response?.data?.getChatRoomMessages
    }

    override suspend fun getRealTimeMessage(roomId: String): Flow<ApolloResponse<RealTimeMessagesSubscription.Data>>? {
        Log.d(TAG, "getRealTimeMessage roomID $roomId")
        val response = try {
            apolloClient().subscription(RealTimeMessagesSubscription(roomId = roomId)).toFlow()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response
    }

    override suspend fun getFriendList(userId: String): List<GetAllFriendsQuery.GetAllFriend?>? {
        Log.d(TAG, "getFriendList userID $userId")
        val response = try {
            apolloClient().query(GetAllFriendsQuery( userId = userId)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.getAllFriends
    }

    override suspend fun getUserByName(userName: String): String? {
        Log.d(TAG, "getUserByName userID $userName")
        val response = try {
            apolloClient().query(GetUserByNameQuery(userName = userName)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.getUserByName?.get(0)?.userId
    }

    override suspend fun getUserById(userId: String): String? {
        Log.d(TAG, "getFriendList userID $userId")
        val response = try {
            apolloClient().query(GetUserNameByIdQuery(userId = userId)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.getUserById?.get(0)?.userName
    }

    override suspend fun getChatRoomById(roomId: String): String? {
        Log.d(TAG, "getChatRoom userID $roomId")
        val response = try {
            apolloClient().query(GetChatRoomNameQuery(roomId = roomId)).execute()
        } catch (e: Exception) {
            System.out.print("Error " + e.message)
            null
        }
        return response?.data?.getChatRoomName
    }
}