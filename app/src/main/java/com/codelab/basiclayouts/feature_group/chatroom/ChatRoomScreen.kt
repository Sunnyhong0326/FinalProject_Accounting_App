package com.codelab.basiclayouts.feature_group.chatroom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.feature_group.MessageData
import com.codelab.basiclayouts.feature_group.component.TopBar
import com.codelab.basiclayouts.ui.theme.Brown100
import com.codelab.basiclayouts.ui.theme.MySootheTheme

@Composable
fun OthersMessageCard(
    @DrawableRes profile: Int,
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(400.dp)
            .padding(8.dp)
    ) {
        Row{
            Image(
                painter = painterResource(profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
            )
            Card(
                backgroundColor = Brown100,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                )
            }
        }
    }
}

@Composable
fun MyMessageCard(
    @DrawableRes profile: Int,
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .width(400.dp)
            .padding(8.dp)
    ) {
        Row{
            Card(
                backgroundColor = Brown100,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                )
            }
            Image(
                painter = painterResource(profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
            )
        }
    }
}



@Composable
fun MessageList(
    messageDataHistory: List<MessageData>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(messageDataHistory) { item ->
            if(item.userId == "1")
                item.message?.let { MyMessageCard(profile = item.profile, message = it) }
            else
                item.message?.let { OthersMessageCard(profile = item.profile, message = it) }
        }
    }
}

@Composable
fun ChatRoomScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier
) {
    var inputValue by remember { mutableStateOf("") }
    val state = groupViewModel.state.value
    Box {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            TopBar(
                onClose = { navController.navigateUp() },
                title = state.currentRoomName
            )
            MessageList(state.chatRoomMessages)
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter)){
            TextField(
                value = inputValue,
                onValueChange = {inputValue = it},
                placeholder = {
                    Text(text ="Write a message...")
                }
            )
            Button(
                modifier = Modifier.height(56.dp),
                onClick = { groupViewModel.sendMessage(inputValue) },
                enabled = inputValue.isNotBlank(),
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ChatRoomScreenPreview() {
    val navController = rememberNavController()
    val messagepViewModel: MessageViewModel = viewModel()
    MySootheTheme(){
        //ChatRoomScreen(navController, GroupViewModel)
    }
}