package com.codelab.basiclayouts.navigation

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codelab.basiclayouts.BottomMenu
import com.codelab.basiclayouts.CardCollectionScreen
import com.codelab.basiclayouts.feature_account.domain.model.PetCard
import com.codelab.basiclayouts.feature_account.domain.model.PetCardType
import com.codelab.basiclayouts.feature_account.presentation.graph.GraphViewModel
import com.codelab.basiclayouts.feature_account.presentation.graph.components.ExpenseGraphScreen
import com.codelab.basiclayouts.feature_account.presentation.graph.components.IncomeGraphScreen
import com.codelab.basiclayouts.feature_account.presentation.homepage.HomepageViewModel
import com.codelab.basiclayouts.feature_account.presentation.homepage.component.HomepageScreen
import com.codelab.basiclayouts.feature_account.presentation.record_list.Date
import com.codelab.basiclayouts.feature_account.presentation.record_list.RecordViewModel
import com.codelab.basiclayouts.feature_account.presentation.record_list.components.RecordScreen
import com.codelab.basiclayouts.feature_account.presentation.search_filter.FilterViewModel
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.FilterResultScreen
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.FilterScreen
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.SearchScreen
import com.codelab.basiclayouts.feature_account.presentation.track_expense.RecordingViewModel
import com.codelab.basiclayouts.feature_account.presentation.track_expense.component.RecordingPageScreen
import com.codelab.basiclayouts.feature_cards.presentation.CardViewModel
import com.codelab.basiclayouts.feature_cards.presentation.card.components.CardScreen
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.feature_group.chatroom.ChatRoomScreen
import com.codelab.basiclayouts.feature_group.chatroom.MessageViewModel
import com.codelab.basiclayouts.feature_group.group_create.InvitePage
import com.codelab.basiclayouts.feature_group.group_create.UserData
import com.codelab.basiclayouts.feature_group.group_create.UserDataType
import com.codelab.basiclayouts.feature_group.group_create.UserViewModel
import com.codelab.basiclayouts.feature_group.group_main.GroupSelectScreen
import com.codelab.basiclayouts.feature_group.group_main.InsideGroupScreen
import com.codelab.basiclayouts.feature_group.members.MemberScreen
import com.codelab.basiclayouts.feature_group.members.ProfileScreen
import com.codelab.basiclayouts.feature_group.tasks.TasksScreen
import com.example.friendly.ui.theme.SettingsScreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Navigation() {

    val homepageViewModel: HomepageViewModel = viewModel()
    val cardViewModel: CardViewModel = viewModel()
    val graphViewModel: GraphViewModel = viewModel()
    val recordViewModel: RecordViewModel = viewModel()
    val filterViewModel: FilterViewModel = viewModel()
    val recordingViewModel: RecordingViewModel = viewModel()
    val groupViewModel: GroupViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val messagepViewModel: MessageViewModel = viewModel()

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomMenu(
                navController,
                onButtonClicked = { screen ->
                    navController.navigate(screen) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomePage.route,
            Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.HomePage.route) {
                HomepageScreen(
                    onClickCollectionBook = { navController.navigate(Screen.CardCollectionScreen.route) },
                    homepageViewModel
                )
            }
            composable(route = Screen.CardCollectionScreen.route) {
                cardViewModel.getCardsFromDatabase()
                CardCollectionScreen(
                    navController,
                    cardViewModel
                )
            }
            composable(route = Screen.ExpenseGraphScreen.route) {
                //graphViewModel.initalInterval()
                graphViewModel.getExpenseDataFromDatabase()
                ExpenseGraphScreen(
                    navController,
                    graphViewModel
                )
            }
            composable(route = Screen.IncomeGraphScreen.route) {
                //graphViewModel.initalInterval()
                graphViewModel.getIncomeDataFromDatabase()
                IncomeGraphScreen(
                    navController,
                    graphViewModel
                )
            }
            composable( route = Screen.RecordScreen.route ) { entry ->
                recordViewModel.goIntoRecordPage()
                RecordScreen(
                    navController,
                    recordViewModel
                )
            }
            composable(route = Screen.SearchScreen.route) {
                filterViewModel.clearNameFilter()
                SearchScreen(
                    navController,
                    filterViewModel
                )
            }
            composable(route = Screen.SortFilterScreen.route) {
                filterViewModel.clearOptionFilter()
                FilterScreen(
                    navController,
                    filterViewModel
                )
            }
            composable(route = Screen.FilterResultScreen.route) {
                FilterResultScreen(
                    navController,
                    filterViewModel
                )
            }
            composable(route = Screen.TrackingScreen.route) {
                RecordingPageScreen(
                    navController,
                    recordingViewModel
                )
            }
            composable(
                route = "petcard" + "/{petCard}",
                arguments = listOf(
                    navArgument("petCard") {
                        type = PetCardType()
                        //defaultValue = ""
                        //nullable = false
                    }
                )
            ) { entry ->
                val petCard = entry.arguments?.getParcelable<PetCard>("petCard")
                if (petCard != null) {
                    CardScreen(
                        navController,
                        cardViewModel,
                        petCard
                    )
                }
            }
            composable(route = Screen.GroupSelectScreen.route) {
                GroupSelectScreen(
                    navController,
                    groupViewModel
                )
            }
            composable(route = Screen.InsideGroupScreen.route) {
                InsideGroupScreen(
                    navController,
                    groupViewModel
                )
            }
            composable(route = Screen.MemberScreen.route) {
                MemberScreen(
                    navController,
                    groupViewModel
                )
            }
            composable(
                route = Screen.ProfileScreen.route + "/{memberData}",
                arguments = listOf(
                    navArgument("memberData") {
                        type = UserDataType()
                        //defaultValue = ""
                        //nullable = false
                    }
                )
            ) { entry ->
                val memberData = entry.arguments?.getParcelable<UserData>("memberData")
                if (memberData != null) {
                    ProfileScreen(
                        navController,
                        userViewModel,
                        memberData
                    )
                }
            }
            composable(route = Screen.TasksScreen.route) {
                TasksScreen(
                    navController
                )
            }
            composable(route = Screen.ChatRoomScreen.route) {
                ChatRoomScreen(
                    navController,
                    groupViewModel
                )
            }
            composable(route = Screen.InviteScreen.route) {
                InvitePage(
                    navController,
                    userViewModel
                )
            }
            composable(route = Screen.SettingScreen.route) {
                SettingsScreen(
                    navController,
                    userViewModel
                )
            }
        }
    }
}