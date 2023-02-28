package com.codelab.basiclayouts

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.feature_group.GroupViewModel
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.ui.theme.MySootheTheme

@Composable
fun BottomMenuItem(
    @StringRes text: Int,
    @DrawableRes drawable: Int,
    onItemClick: () -> Unit,
    //modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.paddingFromBaseline(top = 10.dp, bottom = 5.dp)
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomMenuItemPreview() {
    MySootheTheme {
        BottomMenuItem(
            text = R.string.Chart,
            drawable = R.drawable.chart_menu_icon,
            onItemClick = {}
        )
    }
}

@Composable
fun BottomMenu(
    navController: NavController,
    onButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.menu_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .offset(y = 25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                BottomMenuItem(
                    text = R.string.Chart,
                    drawable = R.drawable.chart_menu_icon,
                    onItemClick = {
                        onButtonClicked(Screen.ExpenseGraphScreen.route)
                    }
                )
                BottomMenuItem(
                    text = R.string.Record,
                    drawable = R.drawable.record_menu_icon,
                    onItemClick = {
                        onButtonClicked(Screen.RecordScreen.route)
                    }
                )
            }
            Image(
                painter = painterResource(R.drawable.add_menu_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = -10.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            onButtonClicked(Screen.TrackingScreen.route)
                        }
                    )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                BottomMenuItem(
                    text = R.string.Group,
                    drawable = R.drawable.group_menu_icon,
                    onItemClick = {
                        onButtonClicked(Screen.GroupSelectScreen.route)
                    }
                )
                BottomMenuItem(
                    text = R.string.Home,
                    drawable = R.drawable.home_menu_icon,
                    onItemClick = {
                        onButtonClicked(Screen.HomePage.route)
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomMenuPreview() {
    val navController = rememberNavController()
    //val groupViewModel = GroupViewModel(accountingUseCases = )
    /*MySootheTheme {
        BottomMenu(
            navController,
            onButtonClicked = { screen ->
                navController.navigate(screen) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            } ,
            groupViewModel
        )
    }*/
}