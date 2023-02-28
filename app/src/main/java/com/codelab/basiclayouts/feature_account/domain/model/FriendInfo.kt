package com.codelab.basiclayouts.feature_account.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FriendInfo(
    var name: String,
    @DrawableRes var avatar: Int,
    var intimacy: Int,
)