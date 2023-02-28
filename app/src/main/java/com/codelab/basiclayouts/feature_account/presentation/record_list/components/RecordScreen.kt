package com.codelab.basiclayouts.feature_account.presentation.record_list.components

import android.widget.CalendarView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.feature_account.presentation.record_list.RecordViewModel
import com.codelab.basiclayouts.feature_account.domain.model.AccountRecord
import com.codelab.basiclayouts.feature_account.presentation.search_filter.FilterViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme


@Composable
fun RecordScreen(
    navController: NavController,
    recordViewModel: RecordViewModel = viewModel()
) {
    MySootheTheme{
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = { MyTopAppBar(navController) },
                modifier = Modifier.fillMaxSize()
            ) { padding->
                Image(
                    painter = painterResource(id = R.drawable.app_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                ListBody(
                    recordViewModel,
                    Modifier.padding(padding)
                )

            }
        }
    }
}

@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun RecordScreenPreview() {
    val navController = rememberNavController()
    RecordScreen(navController)
}


@Composable
fun MyTopAppBar(
    navController: NavController
){
    val color = HexToJetpackColor.getColor("FF8855")

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }

        Spacer(modifier = Modifier.width(60.dp))

        Text(
            text = "Accounting History",
            style = MaterialTheme.typography.h5,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(65.dp))

        IconButton(
            onClick = {
                navController.navigate(Screen.SearchScreen.route)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

// Calender View要修改viewModel的Date資料
@Composable
fun ListBody(
    recordViewModel: RecordViewModel,
    modifier: Modifier = Modifier
){

    var date by remember {
        mutableStateOf(
            recordViewModel.currentDate.year.plus("/")
                .plus(recordViewModel.currentDate.month)
                .plus("/")
                .plus(recordViewModel.currentDate.day)
        )
    }

    Box(modifier = modifier) {
        Column {
            AndroidView(
                factory = { CalendarView(ContextThemeWrapper(it, R.style.CustomCalendar)) },
                //factory = { CalendarView(it) },
                update = {
                    it.setOnDateChangeListener{ calendarView, year, month, day ->
                        recordViewModel.updateCurrentDate(
                            year.toString(),
                            (month+1).toString(),
                            day.toString()
                        )
                        date = recordViewModel.currentDate.year.plus("/")
                            .plus(
                                if(recordViewModel.currentDate.month.length == 1) "0" + recordViewModel.currentDate.month
                                else recordViewModel.currentDate.month
                            )
                            .plus("/")
                            .plus(
                                if(recordViewModel.currentDate.day.length == 1) "0" + recordViewModel.currentDate.day
                                else recordViewModel.currentDate.day
                            )
                        recordViewModel.getRecordDataFromDatabase(date)
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$date",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            RecordCardColumn(recordViewModel.recordData, modifier)
        }
    }
}

@Composable
fun RecordCardColumn(
    recordData: List<AccountRecord>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(recordData) { item ->
            SingleRecordCard(accountRecord = item,  modifier = modifier)
        }
    }
}

@Composable
fun SingleRecordCard(
    accountRecord: AccountRecord,
    modifier: Modifier = Modifier
){
    val e = listOf("飲食","娛樂","學習","交通","用品","醫療")
    val dollarSign: String = if(accountRecord.iconString in e) "-" else "+"
    //val dollarSign: String = if(accountRecord.money>=0) "+" else "-"
    val color = HexToJetpackColor.getColor("FF9747")

    Surface (
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(75.dp),
        color = Color.LightGray.copy(alpha=0.7f)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color)
            ){
                Text(
                    text = accountRecord.iconString,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 15.sp
                )
            }
            //Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = accountRecord.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 16.dp),
                fontSize = 20.sp
            )
            //Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = dollarSign.plus("$").plus(accountRecord.money),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .width(250.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}