package com.codelab.basiclayouts.feature_group.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.R
import com.codelab.basiclayouts.ui.theme.Orange400

@Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    TopBar({},"Test")
}

@Composable
fun TopBar(
    onClose: () -> Unit,
    title: String,
    modifier: Modifier =  Modifier
) {
    Box {
        Image(
            painter = painterResource(R.drawable.topbar_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            color = Orange400,
            border = BorderStroke(1.dp, Color.Black),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .heightIn(min = 55.dp)
                .width(350.dp)
                .padding(4.dp)
                .align(Alignment.Center)
        ) {
            Box {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onClose() }
                        .size(35.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = (-8).dp)
                )
            }
        }
    }
}