package com.codelab.basiclayouts.feature_group.group_main


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.feature_group.groupNames
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.ui.theme.Gray200
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.Orange200

@Preview(showBackground = true)
@Composable
fun  GroupScreenPreview(){
    val navController = rememberNavController()
    val groupViewModel: GroupViewModel = viewModel()
    GroupScreen(navController, groupViewModel)
}

@Composable
fun GroupBottom(
    groupName: String?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Gray200,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(120.dp)
        ) {
            if (groupName != null) {
                Text(
                    text = groupName,
                    style = MaterialTheme.typography.h5,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupsGrid(
    navController: NavController,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier
) {
    val state = groupViewModel.state.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
        ) {
            itemsIndexed(state.userChatRoomNames) { index, item ->
                GroupBottom(
                    groupName = item,
                    onItemClick = {
                        groupViewModel.updateHatchProgress()
                        groupViewModel.setCurrentRoomIdAndName(index)
                        navController.navigate(Screen.InsideGroupScreen.route) } //Navigate到Group
                )
            }
        }
        Card(
            backgroundColor = Orange200,
            elevation = 8.dp,
            modifier = Modifier.clickable { navController.navigate(Screen.InviteScreen.route) } //Navigate to InviteScreen
            ) {
            Text(
                text = "Create!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}
//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun GroupsGridPreview() {
//    FriendlyTheme {
//        GroupsGrid()
//    }
//}

@Composable
fun RefreshButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onClick() },
        enabled = true,
        modifier = Modifier
            .clip(CircleShape)
            .size(80.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun GroupScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Groups",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .paddingFromBaseline(top = 50.dp, bottom = 8.dp)
                    .align(Alignment.Center)
            )
            Box(Modifier.align(Alignment.CenterEnd).absolutePadding(8.dp)) {
                RefreshButton(onClick = { groupViewModel.getAllGroups() })
            }
        }
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier.padding(16.dp)
        )
        GroupsGrid(navController, groupViewModel)
    }
}
//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun GroupScreenPreview() {
//    FriendlyTheme {
//        GroupScreen()
//    }
//}

@Composable
fun GroupSelectScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier
) {
    MySootheTheme() {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxWidth()
            )
            GroupScreen(navController, groupViewModel)
        }
    }
}
//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun GroupScreenFullPreview() {
//    GroupSelectScreen()
//}
