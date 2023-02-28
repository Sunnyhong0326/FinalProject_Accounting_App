package com.codelab.basiclayouts.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_record")
data class UserRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var account_type: String,
    var record_name: String,
    var price: Int,
    var date: String,
    var is_expense: Boolean
)