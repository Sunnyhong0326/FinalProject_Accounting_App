package com.codelab.basiclayouts.feature_account.domain.model

import androidx.compose.ui.graphics.Color

data class AccountTypeTotalData(
    var type: String,
    var percent: Float,
    var color: Color,
    var money: Int
)