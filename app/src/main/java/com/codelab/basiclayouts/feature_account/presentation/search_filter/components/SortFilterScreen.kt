package com.codelab.basiclayouts.feature_account.presentation.search_filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import java.util.logging.Filter


@Composable
fun FilterScreen(
    navController: NavController,
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
){
    MySootheTheme() {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = { FilterTopAppBar(navController) },
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    //Spacer(modifier = Modifier.height(5.dp))
                    FilterBody(navController, filterViewModel)
                }
            }

        }
    }
}

@Composable
fun FilterBody(
    navController: NavController,
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleRow(title = "Sort By")
            Spacer(modifier = Modifier.height(10.dp))
            ToggleButtonRow(
                filterViewModel,
                optionList = listOf(
                    "Date", "Price", "Frequency"
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            TitleRow(title = "Interests")
            // Spacer(modifier = Modifier.height(10.dp))
            InterestBody(filterViewModel)
            // Spacer(modifier = Modifier.height(20.dp))
            TitleRow(title = "Price Range")
            Spacer(modifier = Modifier.height(10.dp))
            PriceRangeRow(filterViewModel)
        }
        ConfirmButtonRow(navController, filterViewModel)
    }
}

@Composable
fun FilterTopAppBar(navController: NavController){

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Spacer(modifier = Modifier.width(110.dp))

            Text(
                text = "Sort and Filter",
                style = MaterialTheme.typography.h5,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(80.dp))

            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun TitleRow(
    title: String,
    modifier: Modifier = Modifier
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.LightGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier.width(10.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ToggleButtonRow(filterViewModel: FilterViewModel, optionList: List<String>) {
    val options = optionList
    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        if(selectedOption == text) {
            selectedOption = ""
            filterViewModel.setFilterOption("sortBy", sortBy = "")
        }
        else {
            selectedOption = text
            filterViewModel.setFilterOption("sortBy", sortBy = text)
        }
    }
    val color = HexToJetpackColor.getColor("FF9747")
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp),
    ) {
        options.forEach { text ->
            Column(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    )
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = text,
                    style = typography.body1.merge(),
                    color = Color.Black,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        )
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                color
                            } else {
                                Color.LightGray
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        )
                        .width(80.dp)
                    ,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PriceRangeRow(
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var text1 by remember {
                mutableStateOf("")
            }
            var text2 by remember {
                mutableStateOf("")
            }
            Spacer(modifier = Modifier.width(20.dp))
            TextField(
                value = text1,
                onValueChange = {
                    text1 = it
                    filterViewModel.setFilterOption("priceLow", priceLow = text1.toIntOrNull())
                },
                label = { Text("LowerBound") },
                modifier = Modifier.width(140.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "~",
                style = MaterialTheme.typography.h5,
                fontSize = 25.sp,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextField(
                value = text2,
                onValueChange = {
                    text2 = it
                    filterViewModel.setFilterOption("priceHigh", priceHigh = text2.toIntOrNull())
                },
                label = { Text("UpperBound") },
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@Composable
fun InterestBody(filterViewModel: FilterViewModel, modifier: Modifier = Modifier){
    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            InterestRow(filterViewModel, typeList = ExpendTypeList)
            Spacer(modifier = Modifier.height(5.dp))
            InterestRow(filterViewModel, typeList = IncomeTypeList)
        }
    }
}

@Composable
fun InterestRow(filterViewModel: FilterViewModel, typeList: List<String>, modifier:Modifier = Modifier){
    val color = HexToJetpackColor.getColor("FF9747")
    val clickedInterests = remember {
        mutableStateListOf<String>()
    }
    val onSelectionChange = { text: String ->
        if(text in clickedInterests){
            clickedInterests.remove(text)
            val tmpList: MutableList<String> = filterViewModel.filterOption.interests.toMutableList()
            tmpList.remove(text)
            filterViewModel.setFilterOption(setTarget = "interests", interests = tmpList)
        }
        else{
            clickedInterests.add(text)
            val tmpList: MutableList<String> = filterViewModel.filterOption.interests.toMutableList()
            tmpList.add(text)
            filterViewModel.setFilterOption(setTarget = "interests", interests = tmpList)
        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ){
        items(typeList) { item ->
            Card(
                modifier = Modifier
                    .padding(5.dp),
                backgroundColor = if (item in clickedInterests) color else Color.LightGray
            ){
                Text(
                    text = item,
                    style = MaterialTheme.typography.h5,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            onSelectionChange(item)
                        }
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        )
                )
            }
        }
    }
}


@Composable
fun ConfirmButtonRow(
    navController: NavController,
    filterViewModel: FilterViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color = HexToJetpackColor.getColor("FF9747")
        Card(
            backgroundColor = color,
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    filterViewModel.getFilterDataByOption()
                    navController.navigate(Screen.FilterResultScreen.route)
                }
                .width(250.dp)
        ) {
            Text(
                text = "Confirm",
                style = MaterialTheme.typography.h5,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            size = 12.dp,
                        ),
                    ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun FilterTopAppBarPreview(){
    val navController = rememberNavController()
    MySootheTheme {
        FilterTopAppBar(navController)
    }
}

@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun TitleRowPreview(){
    MySootheTheme {
        TitleRow("title1")
    }
}

/*
@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun CustomRadioGroupPreview(){
    MySootheTheme {
        ToggleButtonRow(OptionList1)
    }
}
*/

/*
@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun PriceRangeRowPreview(){
    MySootheTheme {
        PriceRangeRow()
    }
}
*/

/*
@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun InterestRowPreview(){
    MySootheTheme {
        InterestRow(typeList = ExpendTypeList)
    }
}
*/

/*
@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun InterestBodyPreview(){
    MySootheTheme {
        InterestBody()
    }
}
 */


@Preview(device = Devices.PIXEL_3_XL)
@Composable
fun FilterScreenPreview(){
    val navController = rememberNavController()
    val filterViewModel: FilterViewModel = viewModel()
    FilterScreen(navController, filterViewModel)
}



val OptionList1 = listOf(
    "Option1",
    "Option2",
    "Option3"
)

val ExpendTypeList = listOf(
    "飲食",
    "娛樂",
    "學習",
    "交通",
    "用品",
    "醫療",
)

val IncomeTypeList = listOf(
    "打工",
    "薪水",
    "獎金",
    "股票",
    "投資",
    "租金"
)
