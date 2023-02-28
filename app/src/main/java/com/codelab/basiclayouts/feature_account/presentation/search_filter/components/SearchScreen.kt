package com.codelab.basiclayouts.feature_account.presentation.search_filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.feature_account.presentation.record_list.components.HexToJetpackColor
import com.codelab.basiclayouts.feature_account.presentation.search_filter.FilterViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme


@Composable
fun SearchScreen(
    navController: NavController,
    filterViewModel: FilterViewModel = viewModel(),
    modifier:Modifier = Modifier
) {
    MySootheTheme() {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = { SearchTopAppBar(navController) },
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_screen_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    SearchBody(navController, filterViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                    // RecordCardColumn(modifier)
                }

            }

        }
    }
}

@Composable
fun SearchTopAppBar(navController: NavController){
    val color = HexToJetpackColor.getColor("FF8855")
    Box(
        modifier = Modifier.fillMaxWidth(),

        ) {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(110.dp))

            Text(
                text = "Search",
                style = MaterialTheme.typography.h5,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(110.dp))

            IconButton(onClick = { navController.navigate(Screen.RecordScreen.route) }) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun InputFilterRow(
    navController: NavController,
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        var text by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.width(10.dp))
        TextField(
            value = text,
            onValueChange = {
                text = it
                filterViewModel.setSearchKey(it)
            },
            label = { Text(text = ("Label"))},
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = {
            filterViewModel.getFilterDataByName()
            navController.navigate(Screen.FilterResultScreen.route)
        }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null ,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(onClick = {
            navController.navigate(Screen.SortFilterScreen.route)
        }) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun SearchBody(
    navController: NavController,
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.fillMaxWidth()) {
        InputFilterRow(navController, filterViewModel)
    }
}

@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun SearchTopBarPreview(){
    val navController = rememberNavController()
    MySootheTheme {
        SearchTopAppBar(navController)
    }
}

/*
@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun InputFilterRowPreview(){
    MySootheTheme {
        InputFilterRow()
    }
}
 */

@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun SearchScreenPreview(){
    val navController = rememberNavController()
    SearchScreen(navController)
}

object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}