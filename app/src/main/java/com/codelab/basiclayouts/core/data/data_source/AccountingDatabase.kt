package com.codelab.basiclayouts.core.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codelab.basiclayouts.core.data.*


@Database(
    entities = [
        UserAccount::class,
        UserCard::class,
        UserFriend::class,
        UserGroup::class,
        UserRecord::class,
    ],
    version = 1
)
abstract class AccountingDatabase : RoomDatabase() {

    abstract val accountingDao: AccountingDao

    companion object {
        const val DATABASE_NAME = "accounting_database"

    }
}