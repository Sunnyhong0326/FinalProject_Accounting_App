package com.codelab.basiclayouts.navigation

sealed class Screen(val route: String) {
    object HomePage : Screen("home_page")
    object ExpenseGraphScreen : Screen("expense_graph_screen")
    object IncomeGraphScreen : Screen("income_graph_screen")
    object RecordScreen : Screen("record_screen")
    object FilterResultScreen : Screen("filter_result_screen")
    object SearchScreen : Screen("search_screen")
    object SortFilterScreen : Screen("sort_filter_screen")
    object TrackingScreen : Screen("tracking_page")

    object CardCollectionScreen : Screen("card_collection_screen")
    object CardScreen : Screen("card_screen")

    object GroupSelectScreen : Screen("group_select_screen")
    object InsideGroupScreen : Screen("inside_group_screen")
    object SettingScreen : Screen("setting_screen")
    object InviteScreen : Screen("invite_screen")
    object MemberScreen : Screen("member_screen")
    object ProfileScreen : Screen("profile_screen")
    object TasksScreen : Screen("tasks_screen")
    object ChatRoomScreen : Screen("chatroom_screen")
}