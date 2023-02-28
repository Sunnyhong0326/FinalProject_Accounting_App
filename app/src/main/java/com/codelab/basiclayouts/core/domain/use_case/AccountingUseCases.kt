package com.codelab.basiclayouts.core.domain.use_case

import android.util.Log
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.core.data.*
import com.codelab.basiclayouts.core.domain.repository.AccountingRepository
import com.codelab.basiclayouts.feature_account.domain.model.*
import com.codelab.basiclayouts.feature_account.presentation.graph.components.timeInterval
import com.codelab.basiclayouts.feature_account.presentation.homepage.friendsList
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.HexToJetpackColor
import com.codelab.basiclayouts.feature_cards.presentation.cardList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

class AccountingUseCases(private val repository: AccountingRepository) {

    // 初始頁面輸入完名字進入主畫面時，使用這個function初始化卡牌資訊
    suspend fun cardInit(){
        for(i in 0..10) {
            repository.addCard(
                UserCard(
                    id = i,
                    available = i==1
                )
            )
        }
    }

    // 初始頁面會輸入名字，從遠端拿到自己的server_id後 就可以呼叫這個function
    suspend fun addAccount(id:Int = 1, server_id: String, user_name: String) {
        repository.addAccount(
            UserAccount(
                id = id,
                server_id = server_id,
                user_name = user_name
            )
        )
    }

    // 重新啟動app時可以看userAccount來決定自己是不是第一次進入app
    // 使用群組功能也需要拿到這個函式回傳的serverID
    // list[0]存的是使用者的名字跟server_ID 這個list只有一個值
    suspend fun getUserAccount(): String {
        val accountTableList = repository.getUserAccount()
        return if(accountTableList.isNotEmpty())
            accountTableList[0].server_id
        else
            "no server ID"
    }

    // 這個function是進入card collection screen的時候要呼叫的
    // 可以知道自己現在有哪幾張卡牌
    // 下面的cardList是固定data 在petCardData.kt裡面
    suspend fun getCardsList(): List<PetCard> {
        val cardTableList = repository.getCardsList()
        val returnList = cardList
        for(i in 0..9){
            returnList[i].available = cardTableList[i].available
        }
        return returnList
    }

    // 當群組中收集到卡片時，傳給這一個function(1-10表示第幾張牌)
    // 接下來他會自動讓這張牌變available
    suspend fun changeCardStatus(cardId: Int) {
        repository.changeCardStatus(cardId)
    }

    // 主畫面會需要這個function來查看好友列表
    // 由於database沒有存照片，目前是用寫死的FriendData.kt中的friendsList
    suspend fun getFriendsList(): List<FriendInfo> {
        val friendTableList = repository.getFriendsList()
        val returnList = friendsList
        for(i in friendTableList.indices){
            if(i> friendsList.size-1){
                returnList[i].avatar = R.drawable.wu
                returnList[i].name = friendTableList[i].name.toString()
                returnList[i].intimacy = friendTableList[i].intimacy
            }
            else{
                returnList[i].name = friendTableList[i].name.toString()
                returnList[i].intimacy = friendTableList[i].intimacy
            }
        }
        return returnList
    }

    // 記帳用的函式 需要傳兩個參數 第一個是你原本的type，第二個是isExpense(是支出)
    suspend fun addRecord(trackInfo: TrackInfo, isExpense: Boolean) {
        val userRecord = UserRecord(
            account_type = trackInfo.category,
            record_name = trackInfo.item,
            price = trackInfo.price,
            date = trackInfo.time,
            is_expense = isExpense
        )
        repository.addRecord(userRecord)
    }

    // 日曆按下去時要把viewModel的時間給這個function
    // 要注意傳進來的date要跟你設定的格式一模一樣
    suspend fun getRecordByDate(date: String): List<AccountRecord> {
        val recordTableList = repository.getRecordByDate(date)

        val returnList = mutableListOf<AccountRecord>()
        for(i in recordTableList.indices){
            returnList.add(
                AccountRecord(
                    name = recordTableList[i].record_name,
                    money = recordTableList[i].price,
                    timeStamp = recordTableList[i].date,
                    iconString = recordTableList[i].account_type
                )
            )
        }
        return returnList.toList()
    }

    // search 紀錄輸入名字後岸搜尋按鈕 要呼叫這個function 然後把viewModel存的textfield值放到name參數
    suspend fun getRecordByName(name: String): List<AccountRecord> {
        val recordTableList = repository.getRecordByName(name)
        val returnList = mutableListOf<AccountRecord>()
        for(i in recordTableList.indices){
            returnList.add(
                AccountRecord(
                    name = recordTableList[i].record_name,
                    money = recordTableList[i].price,
                    timeStamp = recordTableList[i].date,
                    iconString = recordTableList[i].account_type
                )
            )
        }
        return returnList.toList()
    }

    // filter按下確認後進入搜尋結果畫面要顯示的list就是這裡回傳的
    // 要傳入選項filter進來
    suspend fun getRecordByOption(filterOption: FilterOption): List<AccountRecord> {
        val recordTableList = repository.getRecordByOption(filterOption)
        val returnList = mutableListOf<AccountRecord>()
        val priceHighNull: Boolean = filterOption.priceHigh==null
        val priceLowNull: Boolean = filterOption.priceLow==null

        for(i in recordTableList.indices){
            if(
                recordTableList[i].account_type in filterOption.interests
                && ((priceHighNull && priceLowNull)
                        || (priceLowNull && !priceHighNull && recordTableList[i].price < filterOption.priceHigh!!.toInt())
                        || (priceHighNull && !priceLowNull && recordTableList[i].price > filterOption.priceLow!!.toInt())
                        || (!priceLowNull && !priceLowNull && recordTableList[i].price> filterOption.priceLow!!.toInt()
                        && recordTableList[i].price < filterOption.priceHigh!!.toInt()))
            ){
                returnList.add(
                    AccountRecord(
                        name = recordTableList[i].record_name,
                        money = recordTableList[i].price,
                        timeStamp = recordTableList[i].date,
                        iconString = recordTableList[i].account_type
                    )
                )
            }
        }

        if(filterOption.sortBy=="Date"){
            returnList.sortBy {
                it.timeStamp
            }
        }
        else if(filterOption.sortBy=="Price"){
            returnList.sortBy {
                it.money
            }
        }
        else{
            returnList.sortBy {
                it.iconString
            }
        }
        return returnList.toList()
    }

    // 我們要統一時間規格 2022/03/05

    // 這個function會在graph用到 按下Day Year Month會觸發這些
    suspend fun getRecordByTimeInterval(isExpense: Boolean, currentDate: String, timeInterval: String): List<AccountTypeTotalData> {
        val recordTableList = repository.getRecordByTimeInterval(timeInterval)
        val tmpList = mutableListOf<AccountRecord>()
        var returnList = mutableListOf<AccountTypeTotalData>()

        if(!isExpense){
            returnList =  mutableListOf<AccountTypeTotalData>(
                AccountTypeTotalData("打工", 0.0f, HexToJetpackColor.getColor("FF9898"), 0),
                AccountTypeTotalData("薪水", 0.0f, HexToJetpackColor.getColor("5388D8"), 0),
                AccountTypeTotalData("獎金", 0.0f, HexToJetpackColor.getColor("F4BE37"), 0),
                AccountTypeTotalData("股票", 0.0f, HexToJetpackColor.getColor("FAA73C"), 0),
                AccountTypeTotalData("投資", 0.0f, HexToJetpackColor.getColor("FF9040"), 0),
                AccountTypeTotalData("租金", 0.0f, HexToJetpackColor.getColor("00AF54"), 0),
            )
        }
        else{
            returnList =  mutableListOf<AccountTypeTotalData>(
                AccountTypeTotalData("飲食", 0.0f, HexToJetpackColor.getColor("7A96EB"), 0),
                AccountTypeTotalData("娛樂", 0.0f, HexToJetpackColor.getColor("B9E2FC"), 0),
                AccountTypeTotalData("學習", 0.0f, HexToJetpackColor.getColor("F9DA8B"), 0),
                AccountTypeTotalData("交通", 0.0f, HexToJetpackColor.getColor("6CD534"), 0),
                AccountTypeTotalData("用品", 0.0f, HexToJetpackColor.getColor("79EA7A"), 0),
                AccountTypeTotalData("醫療", 0.0f, HexToJetpackColor.getColor("85FFC0"), 0),
            )
        }

        if(timeInterval=="Day") {
            for (i in recordTableList.indices) {
                if(recordTableList[i].date.take(10)==currentDate.take(10)
                    && recordTableList[i].is_expense == isExpense
                ){
                    tmpList.add(
                        AccountRecord(
                            name = recordTableList[i].record_name,
                            money = recordTableList[i].price,
                            timeStamp = recordTableList[i].date,
                            iconString = recordTableList[i].account_type
                        )
                    )
                }
            }
        }
        else if(timeInterval=="Month"){
            for (i in recordTableList.indices) {
                if(recordTableList[i].date.take(7)==currentDate.take(7)
                    && recordTableList[i].is_expense == isExpense
                ){
                    tmpList.add(
                        AccountRecord(
                            name = recordTableList[i].record_name,
                            money = recordTableList[i].price,
                            timeStamp = recordTableList[i].date,
                            iconString = recordTableList[i].account_type
                        )
                    )
                }
            }
        }
        else if(timeInterval=="Year"){
            for (i in recordTableList.indices) {
                if(recordTableList[i].date.take(4)==currentDate.take(4)
                    && recordTableList[i].is_expense == isExpense
                ){
                    tmpList.add(
                        AccountRecord(
                            name = recordTableList[i].record_name,
                            money = recordTableList[i].price,
                            timeStamp = recordTableList[i].date,
                            iconString = recordTableList[i].account_type
                        )
                    )
                }
            }
        }

        for(i in tmpList.indices){
            for(j in returnList.indices){
                if(tmpList[i].iconString==returnList[j].type){
                    returnList[j].money+=tmpList[i].money
                    break
                }
            }
        }

        var totalMoney: Float = 0.0f

        for(i in returnList.indices){
            totalMoney += returnList[i].money.toFloat()
        }

        for(i in returnList.indices){
            returnList[i].percent = returnList[i].money.toFloat()/totalMoney
        }

        if(totalMoney==0.0f){
            if(!isExpense){
                return mutableListOf<AccountTypeTotalData>(
                    AccountTypeTotalData("打工", 0.0f, HexToJetpackColor.getColor("FF9898"), 0),
                    AccountTypeTotalData("薪水", 0.0f, HexToJetpackColor.getColor("5388D8"), 0),
                    AccountTypeTotalData("獎金", 0.0f, HexToJetpackColor.getColor("F4BE37"), 0),
                    AccountTypeTotalData("股票", 0.0f, HexToJetpackColor.getColor("FAA73C"), 0),
                    AccountTypeTotalData("投資", 0.0f, HexToJetpackColor.getColor("FF9040"), 0),
                    AccountTypeTotalData("租金", 0.0f, HexToJetpackColor.getColor("00AF54"), 0),
                )
            }
            else{
                return mutableListOf<AccountTypeTotalData>(
                    AccountTypeTotalData("飲食", 0.0f, HexToJetpackColor.getColor("7A96EB"), 0),
                    AccountTypeTotalData("娛樂", 0.0f, HexToJetpackColor.getColor("B9E2FC"), 0),
                    AccountTypeTotalData("學習", 0.0f, HexToJetpackColor.getColor("F9DA8B"), 0),
                    AccountTypeTotalData("交通", 0.0f, HexToJetpackColor.getColor("6CD534"), 0),
                    AccountTypeTotalData("用品", 0.0f, HexToJetpackColor.getColor("79EA7A"), 0),
                    AccountTypeTotalData("醫療", 0.0f, HexToJetpackColor.getColor("85FFC0"), 0),
                )
            }
        }

        return returnList.toList()
    }

}

