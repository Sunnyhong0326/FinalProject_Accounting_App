package com.codelab.basiclayouts.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_card")
data class UserCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var available: Boolean
)
