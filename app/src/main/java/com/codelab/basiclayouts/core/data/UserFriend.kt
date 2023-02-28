package com.codelab.basiclayouts.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_friend")
data class UserFriend(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val server_id: String? = null,
    var name: String? = null,
    var intimacy: Int
)