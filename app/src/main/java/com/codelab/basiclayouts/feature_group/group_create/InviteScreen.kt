package com.codelab.basiclayouts.feature_group.group_create

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.ui.theme.Gray200
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.Orange200


@Composable
fun FriendCard(
    userViewModel: UserViewModel,
    userData: UserData,
    modifier: Modifier = Modifier
) {
    var selected by remember{
        mutableStateOf(false)
    }

    Surface(
        color = Gray200,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(userData.profile),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = userData.name,
                style = MaterialTheme.typography.h5,
                modifier = modifier
            )
            RadioButton(
                selected = selected,
                onClick = {
                    selected = !selected
                    userViewModel.select(userData, selected)
                }
            )
        }
    }
}
//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun FriendCardPreview() {
//    FriendlyTheme {
//        FriendCard(
//            name = R.string.friend_1,
//            profile = R.drawable.friend_1,
//            onItemClick = {}
//        )
//    }
//}


@Composable
fun SearchFriendGrid(
    userViewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(userViewModel.friendDataList) { item ->
            if (item.name.contains(userViewModel.searchKey)) {
                FriendCard(
                    userViewModel = userViewModel,
                    userData = item
                )
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchFriendGrid() {
    MySootheTheme {
        SearchFriendGrid()
    }
}

@Composable
fun InviteSearchBar(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }
    TextField(
        value = text,
        onValueChange = {
            text = it
            userViewModel.setSearchKey(it)
                        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .padding(16.dp)
    )
}

@Composable
fun InvitePage(
    navController: NavController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigateUp() }
                        .size(35.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 55.dp))
                Text(
                    text = "Invite",
                    style = MaterialTheme.typography.h4,
                )
                Spacer(modifier = Modifier.fillMaxWidth())
            }
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp)
            )
            InviteSearchBar(userViewModel)
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp)
            )
            SearchFriendGrid(userViewModel)
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-30).dp)
        ) {
            Surface(
                color = Orange200,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.SettingScreen.route)
                    } //Navigate到SettingScreen
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(220.dp)
                        .heightIn(40.dp)
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun InvitePagePreview() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    MySootheTheme {
        InvitePage(navController, userViewModel)
    }
}