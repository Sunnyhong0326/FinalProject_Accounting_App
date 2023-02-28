package com.codelab.basiclayouts.feature_group.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.feature_group.TaskData
import com.codelab.basiclayouts.feature_group.component.TopBar
import com.codelab.basiclayouts.feature_group.taskList
import com.codelab.basiclayouts.ui.theme.Brown400
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.Orange200
import com.codelab.basiclayouts.ui.theme.Yellow400
import kotlin.math.roundToInt



@Composable
fun TaskCard(
    taskData: TaskData,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        backgroundColor = Yellow400,
        border = BorderStroke(4.dp, Brown400),
        elevation = 8.dp,
        modifier = Modifier
            .width(350.dp)
            .padding(8.dp)
    ) {
        Box (modifier = Modifier.padding(16.dp)) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = taskData.description,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.widthIn(max = 240.dp)
                )
                Text(
                    text = "孵化進度 +${(taskData.award * 100).roundToInt()}%",
                    style = MaterialTheme.typography.h6.copy(fontSize = 16.sp)
                )
            }
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                CircularProgressIndicator(
                    color = Orange200,
                    progress = taskData.progress,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
                Text(
                    text = "${(taskData.progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(taskList) { item ->
            TaskCard(taskData = item)
        }
    }
}

@Composable
fun TasksScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box {
        Image(
            painter = painterResource(R.drawable.light_group_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            TopBar(
                onClose = { navController.navigateUp() },
                title = "Tasks"
            )
            TaskList()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun TasksScreenPreview() {
    val navController = rememberNavController()
    MySootheTheme() {
        TasksScreen(navController)
    }
}