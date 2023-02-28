package com.codelab.basiclayouts.feature_account.presentation.homepage.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.feature_account.domain.model.FriendInfo
import com.codelab.basiclayouts.feature_account.presentation.homepage.HomepageViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.Orange400

@Composable
fun CollectionBook(onClickCollectionBook: () -> Unit = {}, homepageViewModel: HomepageViewModel){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(R.drawable.book),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clickable(
                    enabled = true,
                    onClickLabel = "Book",
                    onClick = {
                        onClickCollectionBook()
                        homepageViewModel.addCards()
                    }
                )
        )
        Text(text = "Book")
    }
}

@Composable
fun SearchBar()
{
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {text = it},
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
        maxLines = 1,
        modifier = Modifier.width(300.dp)
    )
}

@Composable
fun Basic(
    name: String?,
    value: Int
) {
    Column(modifier = Modifier.width(170.dp)) {
        if (name != null) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painter = painterResource(R.drawable.heart),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.intimacy),
            )
            Text(
                text = value.toString(),
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
fun Friend(
    name: String?,
    @DrawableRes avatar: Int,
    intimacy: Int,
){
    Surface(
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Basic(name,intimacy)
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                painterResource(R.drawable.reminder),
                contentDescription ="reminder icon",
                modifier = Modifier
                    .size(60.dp)
                    .clickable(
                        enabled = true,
                        onClickLabel = "Reminder",
                        onClick = {}
                    )

            )
        }
    }
}

@Composable
fun FriendList(friendList: List<FriendInfo>){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 15.dp)
    ) {
        items(friendList) { item ->
            Friend(item.name, item.avatar, item.intimacy)
        }
    }
}

@Composable
fun HomepageScreen(
    onClickCollectionBook: () -> Unit,
    homepageViewModel: HomepageViewModel
){
    MySootheTheme{
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .fillMaxWidth()
            ){
                CollectionBook(onClickCollectionBook, homepageViewModel)
            }
            Column {
                Image(
                    painter = painterResource(R.drawable.jirachi),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.question_mark),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .align(Alignment.Start)
                        .clickable(
                            enabled = true,
                            onClickLabel = "Homepage_Teaching",
                            onClick = {}
                        )
                )
                Spacer(modifier = Modifier.height(270.dp))
                Text(
                    text = "FRIENDS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically){
                    SearchBar()
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Sharing",
                                onClick = {}
                            )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                FriendList(homepageViewModel.friendList)
            }
        }
    }
}

@Composable
fun Bell(
    newRemind: Boolean, //是否有新的記帳提醒
    modifier: Modifier = Modifier
) {
    Box{
        IconButton(
            onClick = { /*TODO*/ },
            enabled = true,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                    color = Orange400
                )
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White
            )
        }
        if (newRemind) {
            Surface(
                shape = CircleShape,
                color = Color.Red,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.TopEnd)
            ) {}
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun BellPreview() {
//    FriendlyTheme {
//        Bell(newRemind = true)
//   }
//}
@Preview(showBackground = true)
@Composable
fun FriendPreview() {
    Friend("Wu Shan Hung", R.drawable.wu, 92)
}

@Preview(showBackground = true, device = Devices.PIXEL_3_XL)
@Composable
fun HomepagePreview() {
    val homepageViewModel: HomepageViewModel = viewModel()
    val navController = rememberNavController()
    HomepageScreen(
        onClickCollectionBook = { navController.navigate(Screen.CardCollectionScreen.route) },
        homepageViewModel
    )
}