package com.codelab.basiclayouts.feature_account.presentation.graph.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.HexToJetpackColor
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.feature_account.domain.model.AccountTypeTotalData
import com.codelab.basiclayouts.feature_account.presentation.graph.GraphViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import kotlin.math.roundToInt



@Composable
fun IncomeGraphScreen(
    navController: NavController,
    graphViewModel: GraphViewModel,
    modifier: Modifier = Modifier)
{
    MySootheTheme() {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.graph_background),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    //Spacer(modifier = Modifier.height(5.dp))
                    IncomeScreenTopAppBar(navController)
                    IncomeGraphBody(
                        graphViewModel,
                        graphViewModel.incomeTypePercentageData,
                        graphViewModel.incomeMoneyData
                    )
                }
            }

        }
    }
}

@Composable
fun IncomeScreenTopAppBar(
    navController: NavController,
    modifier: Modifier = Modifier
){
    Box(Modifier.background(Color.LightGray)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(100.dp))
            Surface(
                shape = RoundedCornerShape(size = 12.dp),
                color = Color.White,
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "Income",
                    color = Color.Black,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            // Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Spacer(modifier = Modifier.height(15.dp))
                Surface(
                    shape = RoundedCornerShape(size = 12.dp),
                    color = Color.White,
                ) {
                    Text(
                        text = "Expense",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Book",
                                onClick = { navController.navigate(Screen.ExpenseGraphScreen.route) }
                            )
                    )
                }
            }

        }
    }
}

@Composable
fun IncomeGraphBody(
    graphViewModel: GraphViewModel,
    incomeTypePercentageData: List<AccountTypeTotalData>,
    incomeMoneyData: List<Float>,
    modifier: Modifier = Modifier
){
    Column(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphToggleButtonRow(graphViewModel, timeInterval, false)
            Spacer(modifier = Modifier.height(10.dp))
            PieChart(
                modifier = Modifier.size(200.dp),
                progress = incomeMoneyData,
                colors = incomeGraphColorList.take(incomeMoneyData.size)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(15.dp))
                TypePercentageGrid(incomeTypePercentageData)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            TextRow(false)
            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color.Black, thickness = 2.dp)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TypeMoneyCardColumn(incomeTypePercentageData)
        }
    }
}

@Composable
fun TextRow(isExpense: Boolean = false,modifier: Modifier = Modifier){
    Row(modifier) {
        Spacer(modifier = Modifier.width(50.dp))
        Text(
            text = if(isExpense) "支出明細" else "收入明細:",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun TypePercentageGrid(typePercentageData: List<AccountTypeTotalData>, modifier: Modifier = Modifier){
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(50.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = modifier
                .height(80.dp)
                .width(300.dp)
        ) {
            items(typePercentageData) { item ->
                TypePercentageCard(item.type, (item.percent*100).roundToInt(), item.color)
            }
        }
        Spacer(modifier = Modifier.width(100.dp))
    }

}

@Composable
fun TypePercentageCard(
    text: String,
    percent: Int,
    color: Color,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .width(90.dp)
            .height(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text.plus(" ${percent}%"),
            style = MaterialTheme.typography.body1.merge(),
            color = Color.Black,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 12.dp,
                    ),
                )
                .background(
                    color
                )
                .padding(9.dp)
                .width(90.dp)
                .height(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TypeMoneyCardColumn(
    typePercentageData: List<AccountTypeTotalData>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(typePercentageData) { item ->
            TypeMoneyCard(item.type, item.money, item.color)
        }
    }
}

@Composable
fun TypeMoneyCard(
    text: String,
    money: Int,
    color: Color,
    modifier: Modifier = Modifier
){
    Surface (
        modifier = modifier
            .height(50.dp)
            .width(250.dp),
        color = color
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            //Spacer(modifier = Modifier.width(10.dp))

            //Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .width(80.dp)
                    .padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = " $".plus(money),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .width(100.dp)
                    .padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun IncomeScreenTopAppBarPreview(){
    val navController = rememberNavController()
    MySootheTheme {
        IncomeScreenTopAppBar(navController)
    }
}

@Preview
@Composable
fun IncomeGraphScreenPreview(){
    val navController = rememberNavController()
    val graphViewModel: GraphViewModel = viewModel()
    IncomeGraphScreen(navController, graphViewModel)
}


val timeInterval = listOf<String>(
    "Day", "Month", "Year"
)

val incomeGraphColorList = listOf(
    HexToJetpackColor.getColor("FF9898"),
    HexToJetpackColor.getColor("5388D8"),
    HexToJetpackColor.getColor("F4BE37"),
    HexToJetpackColor.getColor("FAA73C"),
    HexToJetpackColor.getColor("FF9040"),
    HexToJetpackColor.getColor("00AF54")
)

/*
val incomeMoneyData = listOf<Float>(
    300f, 200f, 150f, 150f, 100f, 100f
)

val incomeTypePercentageData = listOf(
    AccountTypeTotalData("打工", 0.3f, HexToJetpackColor.getColor("FF9898"), 300),
    AccountTypeTotalData("薪水", 0.2f, HexToJetpackColor.getColor("5388D8"), 200),
    AccountTypeTotalData("獎金", 0.15f, HexToJetpackColor.getColor("F4BE37"), 150),
    AccountTypeTotalData("股票", 0.15f, HexToJetpackColor.getColor("FAA73C"), 150),
    AccountTypeTotalData("投資", 0.1f, HexToJetpackColor.getColor("FF9040"), 100),
    AccountTypeTotalData("租金", 0.1f, HexToJetpackColor.getColor("00AF54"), 100),
)
*/