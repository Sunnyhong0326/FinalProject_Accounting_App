package com.codelab.basiclayouts.core.data.data_source

import androidx.room.*
import com.codelab.basiclayouts.core.data.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(userCard: UserCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(userAccount: UserAccount)

    @Query("SELECT * FROM user_account")
    suspend fun getUserAccount(): List<UserAccount>

    @Query("SELECT * FROM user_card")
    suspend fun getCardsList(): List<UserCard>

    @Query("Update user_card SET available = 1 WHERE id = :cardId")
    suspend fun changeCardStatus(cardId: Int)

    @Query("SELECT * FROM user_friend")
    suspend fun getFriendsList(): List<UserFriend>

    @Query("SELECT * FROM user_group")
    suspend fun getGroupsList(): List<UserGroup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(userRecord: UserRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriend(userFriend: UserFriend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGroup(userGroup: UserGroup)

    @Query("SELECT * FROM user_record WHERE date = :date")
    suspend fun getRecordByDate(date: String): List<UserRecord>

    @Query("SELECT * FROM user_record WHERE record_name = :name")
    suspend fun getRecordByName(name: String): List<UserRecord>

    @Query("SELECT * FROM user_record")
    suspend fun getAllRecords(): List<UserRecord>

}