package com.codelab.basiclayouts.core.domain.repository

import com.codelab.basiclayouts.core.data.*
import com.codelab.basiclayouts.feature_account.domain.model.FilterOption
import kotlinx.coroutines.flow.Flow

interface AccountingRepository {


    // 初始畫面用到的
    suspend fun addCard(userCard: UserCard)

    suspend fun addAccount(userAccount: UserAccount)

    suspend fun getUserAccount():List<UserAccount>

    // 主畫面用到的
    suspend fun getCardsList(): List<UserCard>

    suspend fun getFriendsList(): List<UserFriend>

    // 群組頁面用到的
    suspend fun getGroupsList(): List<UserGroup>


    suspend fun changeCardStatus(cardId: Int)
    // setGroupList 從遠端更新群組

    // 記帳頁面用到的
    suspend fun addRecord(userRecord: UserRecord)

    // 紀錄頁面用到的

    suspend fun getRecordByDate(date: String): List<UserRecord>

    suspend fun getRecordByName(name: String): List<UserRecord>

    suspend fun getRecordByOption(filterOption: FilterOption): List<UserRecord>

    // 圖表頁面用到的
    suspend fun getRecordByTimeInterval(timeInterval: String): List<UserRecord>

}