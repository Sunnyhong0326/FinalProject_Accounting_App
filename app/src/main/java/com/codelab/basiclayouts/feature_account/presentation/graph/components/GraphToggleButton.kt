package com.codelab.basiclayouts.feature_account.presentation.graph.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.codelab.basiclayouts.feature_account.presentation.graph.GraphViewModel
import com.codelab.basiclayouts.feature_account.presentation.record_list.components.HexToJetpackColor

@Composable
fun GraphToggleButtonRow(graphViewModel: GraphViewModel , optionList: List<String>, is_expense: Boolean) {
    val options = optionList
//    var selectedOption by remember {
//        mutableStateOf("Day")
//    }
//    val onSelectionChange = { text: String ->
//        selectedOption = text
//        graphViewModel.setGraphInterval(text)
//    }
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
                    style = MaterialTheme.typography.body1.merge(),
                    color = Color.Black,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        )
                        .clickable {
                            graphViewModel.setGraphInterval(text)
                            if(is_expense) graphViewModel.getExpenseDataFromDatabase()
                            else graphViewModel.getIncomeDataFromDatabase()
                        }
                        .background(
                            if (text == graphViewModel.graphInterval) {
                                color
                            } else {
                                Color.White
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