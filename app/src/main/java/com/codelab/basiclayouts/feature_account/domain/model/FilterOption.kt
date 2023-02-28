package com.codelab.basiclayouts.feature_account.domain.model

data class FilterOption(
    var sortBy: String = "Date",
    var interests: List<String> = listOf(),
    var priceLow: Int? = 0,
    var priceHigh: Int? = 10000000
)