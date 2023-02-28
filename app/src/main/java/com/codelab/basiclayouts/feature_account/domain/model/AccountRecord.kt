package com.codelab.basiclayouts.feature_account.domain.model

/* enum class AccountType {
    FOOD, EDUCATION, CLOTHS, ENTERTAINMENT, SALARY, BONUS, LOTTERY
}
*/

data class AccountRecord(
    // var accountType: AccountType,
    var name: String,
    var money: Int,
    var timeStamp: String,
    var iconString: String
)

