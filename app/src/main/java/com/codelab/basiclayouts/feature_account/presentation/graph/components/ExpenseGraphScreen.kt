package com.codelab.basiclayouts.feature_account.presentation.graph.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.navigation.Screen
import com.codelab.basiclayouts.feature_account.domain.model.AccountTypeTotalData
import com.codelab.basiclayouts.feature_account.presentation.graph.GraphViewModel
import com.codelab.basiclayouts.feature_account.presentation.search_filter.components.HexToJetpackColor
import com.codelab.basiclayouts.ui.theme.MySootheTheme


@Composable
fun ExpenseGraphScreen(
    navController: NavController,
    graphViewModel: GraphViewModel,
    modifier: Modifier = Modifier
){
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
                    ExpenseScreenTopAppBar(navController)
                    ExpenseGraphBody(
                        graphViewModel,
                        graphViewModel.expenseTypePercentageData,
                        graphViewModel.expenseMoneyData
                    )
                }
            }

        }
    }
}

@Composable
fun ExpenseScreenTopAppBar(
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
            Column(modifier = Modifier.fillMaxHeight()) {
                Spacer(modifier = Modifier.height(15.dp))
                Surface(
                    shape = RoundedCornerShape(size = 12.dp),
                    color = Color.White,
                ) {
                    Text(
                        text = "Income",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Book",
                                onClick = { navController.navigate(Screen.IncomeGraphScreen.route) }
                            )
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(size = 12.dp),
                color = Color.White,
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "Expense",
                    color = Color.Black,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            // Spacer(modifier = Modifier.width(20.dp))


        }
    }
}

@Composable
fun ExpenseGraphBody(
    graphViewModel: GraphViewModel,
    expenseTypePercentageData: List<AccountTypeTotalData>,
    expenseMoneyData: List<Float>,
    modifier: Modifier = Modifier
){
    Column(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphToggleButtonRow(graphViewModel, timeInterval, true)
            Spacer(modifier = Modifier.height(10.dp))
            PieChart(
                modifier = Modifier.size(200.dp),
                progress = expenseMoneyData,
                colors = expenseGraphColorList.take(expenseMoneyData.size)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(15.dp))
                TypePercentageGrid(expenseTypePercentageData)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            TextRow(true)
            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color.Black, thickness = 2.dp)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TypeMoneyCardColumn(expenseTypePercentageData)
        }
    }
}

@Preview
@Composable
fun ExpenseScreenTopAppBarPreview(){
    val navController = rememberNavController()
    MySootheTheme {
        ExpenseScreenTopAppBar(navController)
    }
}

@Preview
@Composable
fun ExpenseGraphScreenPreview(){
    val navController = rememberNavController()
    val graphViewModel: GraphViewModel = viewModel()
    ExpenseGraphScreen(navController, graphViewModel)
}


val expenseGraphColorList = listOf(
    HexToJetpackColor.getColor("7A96EB"),
    HexToJetpackColor.getColor("B9E2FC"),
    HexToJetpackColor.getColor("F9DA8B"),
    HexToJetpackColor.getColor("6CD534"),
    HexToJetpackColor.getColor("79EA7A"),
    HexToJetpackColor.getColor("85FFC0")
)

/*

val expenseTypePercentageData = listOf(
    AccountTypeTotalData("飲食", 0.3f, HexToJetpackColor.getColor("7A96EB"), 300),
    AccountTypeTotalData("娛樂", 0.2f, HexToJetpackColor.getColor("B9E2FC"), 200),
    AccountTypeTotalData("學習", 0.15f, HexToJetpackColor.getColor("F9DA8B"), 150),
    AccountTypeTotalData("交通", 0.15f, HexToJetpackColor.getColor("6CD534"), 150),
    AccountTypeTotalData("用品", 0.1f, HexToJetpackColor.getColor("79EA7A"), 100),
    AccountTypeTotalData("醫療", 0.1f, HexToJetpackColor.getColor("85FFC0"), 100),
)



val expenseMoneyData = listOf<Float>(
    300f, 200f, 150f, 150f, 100f, 100f
)

 */