package com.codelab.basiclayouts.feature_account.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetCard(
    @DrawableRes val picture: Int,
    @DrawableRes val shadow: Int,
    @DrawableRes val background: Int,
    val borderColor: String,
    var available: Boolean,
    val name: String,
    val height: Float,
    val weight: Int,
    val isMale: Boolean,
    val description: String
): Parcelable

class PetCardType : NavType<PetCard>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PetCard? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): PetCard {
        return Gson().fromJson(value, PetCard::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: PetCard) {
        bundle.putParcelable(key, value)
    }
}