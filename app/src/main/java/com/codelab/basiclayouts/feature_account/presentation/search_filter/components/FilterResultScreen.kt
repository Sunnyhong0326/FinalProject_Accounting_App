package com.codelab.basiclayouts.feature_account.presentation.search_filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_account.domain.model.FilterOption
import com.codelab.basiclayouts.feature_account.presentation.record_list.components.RecordCardColumn
import com.codelab.basiclayouts.feature_account.presentation.search_filter.FilterViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme

@Composable
fun FilterResultScreen(
    navController: NavController,
    filterViewModel: FilterViewModel = viewModel(),
    modifier: Modifier = Modifier
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
                    painter = painterResource(id = R.drawable.app_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    SearchBody(navController, filterViewModel)
                    //Spacer(modifier = Modifier.height(5.dp))
                    SortByRow(filterOpt)
                    Spacer(modifier = Modifier.height(20.dp))
                    RecordCardColumn(filterViewModel.recordData, modifier)
                }

            }

        }
    }
}

@Composable
fun SortByRow(filterInfo: FilterOption, modifier: Modifier = Modifier){
    val color = HexToJetpackColor.getColor("F24B03")
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(35.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sort by: ${filterInfo.sortBy}",
                style = MaterialTheme.typography.h5,
                fontSize = 10.sp,
                color = color
            )
            Text(
                text = "Interests: ${filterInfo.interests.joinToString(", ")}",
                style = MaterialTheme.typography.h5,
                fontSize = 10.sp,
                color = color
            )
            Text(
                text = "Range:: ${filterInfo.priceLow} to ${filterInfo.priceHigh}",
                style = MaterialTheme.typography.h5,
                fontSize = 10.sp,
                color = color
            )
        }
    }
}

val filterOpt = FilterOption(
    "Date",
    listOf(
        "飲食",
        "獎金",
        "服裝",
        "教育"
    ),
    0,
    5000
)

@Preview(showBackground = true)
@Composable
fun FilterResultScreenPreview(){
    val filterViewModel: FilterViewModel = viewModel()
    val navController = rememberNavController()
    FilterResultScreen(navController, filterViewModel)
}