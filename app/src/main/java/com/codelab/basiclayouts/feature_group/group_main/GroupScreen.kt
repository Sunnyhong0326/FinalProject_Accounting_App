package com.codelab.basiclayouts.feature_group.group_main

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.feature_group.MonsterData
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.ui.theme.*

private val topButtonHeight = 45.dp
private val topButtonWidth = 53.dp

@Composable
fun TasksButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Orange400,
        border = BorderStroke(1.dp, Color.Black),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .heightIn(min = topButtonHeight)
            .width(topButtonWidth)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(R.drawable.task_button),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "Tasks",
                    style = MaterialTheme.typography.h6.copy(fontSize = 10.sp),
                    modifier = Modifier.offset(y = (-4).dp)
                )
            }
        }
    }
}

@Composable
fun MembersButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Orange400,
        border = BorderStroke(1.dp, Color.Black),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .heightIn(min = topButtonHeight)
            .width(topButtonWidth)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(R.drawable.friend_button),
                    contentDescription = null,
                    modifier = Modifier.size(37.dp)
                )
                Text(
                    text = "Members",
                    style = MaterialTheme.typography.h6.copy(fontSize = 10.sp),
                    modifier = Modifier.offset(y = (-10).dp)
                )
            }
        }
    }
}

@Composable
fun GroupName(
    name: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Orange400,
        border = BorderStroke(1.dp, Color.Black),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .heightIn(min = 55.dp)
            .width(170.dp)
            .padding(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = name,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }
    }
}

@Composable
fun GroupTopBar(
    navController: NavController,
    groupViewModel: GroupViewModel,
    name: String,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.topbar_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            GroupName(name)
            MembersButton(onClick = { navController.navigate(Screen.MemberScreen.route) })
            TasksButton(onClick = {
                groupViewModel.updateTasks();
                navController.navigate(Screen.TasksScreen.route)
            })
        }
    }
}

@Composable
fun MonstersInParkRow(
    groupViewModel: GroupViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    LazyRow {
        items(groupViewModel.monstersInPark) { item ->
            Image(
                painter = painterResource(item.picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .width(100.dp)
            )
        }
    }
}
//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun MonstersInParkPreview() {
//    FriendlyTheme {
//        MonstersInPark()
//    }
//}

@Composable
fun CollectBook(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.book),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = "收集冊",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ChatroomButton(
//    @StringRes notificationNumber: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(90.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.chatroom_button),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun HatchProgressBar(
    onClick: () -> Unit,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box {
        Surface(
            color = Brown400,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    color = Yellow200,
                    modifier = Modifier
                        .padding(16.dp)
                        .offset(x = (-60).dp)
                        .height(16.dp)
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.hatch_button),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterEnd)
                .clickable { onClick() }
        )
    }
}

@Composable
fun Egg(
    readyToHatch: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(100.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.egg),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )
        if(readyToHatch) {
            Image(
                painter = painterResource(R.drawable.ready_to_hatch),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun NewMonsterCard(
    onClickCollect: () -> Unit,
    monsterData: MonsterData,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "New Monster!",
                style = MaterialTheme.typography.h4
            )
            Card(
                backgroundColor = Brown100,
                border = BorderStroke(2.dp, Green400)
            ) {
                Image(
                    painter = painterResource(R.drawable.monster_6),
                    contentDescription = null,
                    modifier = Modifier.padding(24.dp)
                )
            }
            Text(
                text = "",
                style = MaterialTheme.typography.h5
            )
            Button(
                onClick = { onClickCollect() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange200)
            ) {
                Text(
                    text = "Collect",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun InsideGroupScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier
) {
    var showCollectCard by remember {
        mutableStateOf(false)
    }
    val state = groupViewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.group_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        GroupTopBar(
            navController,
            groupViewModel = groupViewModel,
            name = state.currentRoomName,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(modifier = Modifier.offset(x = 100.dp, y = (-60).dp)){
            }
            MonstersInParkRow(groupViewModel)
            Egg(readyToHatch = groupViewModel.readyToHatch)
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(modifier = Modifier.offset(x = (-16).dp, y = -8.dp)) {
                ChatroomButton(
                    onClick = {
                        groupViewModel.listenToMessages()
                        navController.navigate(Screen.ChatRoomScreen.route)
                    }
                )
            }
            HatchProgressBar(
                onClick = {
                    if(groupViewModel.readyToHatch) groupViewModel.hatch()
                    showCollectCard = true
                },
                progress = groupViewModel.hatchProgress,
                //progress = 1f, // For demo
                modifier = Modifier.offset(y = 5.dp)
            )
        }
        Box(modifier = Modifier.align(Alignment.Center)){
            if (showCollectCard) {
                NewMonsterCard(
                    onClickCollect = { showCollectCard = false },
                    monsterData = groupViewModel.newHatchedMonster,
                )
            }
        }
    }
//    Surface(
//        color = Color.White
//    ) {
//        Box(modifier = Modifier.fillMaxWidth()) {
//        }
//    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun InsideGroupPreview() {
    val navController = rememberNavController()
    val groupViewModel: GroupViewModel = viewModel()
    MySootheTheme() {
        InsideGroupScreen(
            navController,
            groupViewModel
        )
    }
}

