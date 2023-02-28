package com.codelab.basiclayouts.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_group")
data class UserGroup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val server_id: String,
    var group_name: String,
)
