package com.codelab.basiclayouts.core.data.repository

import com.codelab.basiclayouts.core.data.*
import com.codelab.basiclayouts.core.data.data_source.AccountingDao
import com.codelab.basiclayouts.core.domain.repository.AccountingRepository
import com.codelab.basiclayouts.feature_account.domain.model.FilterOption
import kotlinx.coroutines.flow.Flow

class AccountingRepositoryImpl(
    private val dao: AccountingDao
) : AccountingRepository {
    override suspend fun addCard(userCard: UserCard) {
        dao.addCard(userCard)
    }

    override suspend fun addAccount(userAccount: UserAccount) {
        dao.addAccount(userAccount)
    }

    override suspend fun getUserAccount(): List<UserAccount> {
        return dao.getUserAccount()
    }

    override suspend fun getCardsList(): List<UserCard> {
        return dao.getCardsList()
    }

    override suspend fun getFriendsList(): List<UserFriend> {
        return dao.getFriendsList()
    }

    override suspend fun getGroupsList(): List<UserGroup> {
        return dao.getGroupsList()
    }

    override suspend fun changeCardStatus(cardId: Int) {
        dao.changeCardStatus(cardId)
    }


    override suspend fun addRecord(userRecord: UserRecord) {
        dao.addRecord(userRecord)
    }


    override suspend fun getRecordByDate(date: String): List<UserRecord> {
        return dao.getRecordByDate(date)
    }

    override suspend fun getRecordByName(name: String): List<UserRecord> {
        return dao.getRecordByName(name)
    }

    override suspend fun getRecordByOption(filterOption: FilterOption): List<UserRecord> {
        return dao.getAllRecords()
    }

    override suspend fun getRecordByTimeInterval(timeInterval: String): List<UserRecord> {
        return dao.getAllRecords()
    }



}