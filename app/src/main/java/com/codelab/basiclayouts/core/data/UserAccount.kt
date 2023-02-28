package com.codelab.basiclayouts.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class UserAccount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val server_id: String,
    val user_name: String
)
