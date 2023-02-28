package com.codelab.basiclayouts

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codelab.basiclayouts.feature_account.domain.model.PetCard
import com.codelab.basiclayouts.feature_cards.presentation.CardViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.google.gson.Gson

@Composable
fun CardCollectionScreen(
    navController: NavController,
    cardViewModel: CardViewModel,
    modifier:Modifier = Modifier
){
    MySootheTheme() {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = { CardCollectionTopAppBar(navController) },
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card_collection_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(30.dp))
                    CollectionBody(navController, cardViewModel)
                    //Spacer(modifier = Modifier.height(20.dp))
                    // RecordCardColumn(modifier)
                }

            }

        }
    }
}

@Composable
fun CardCollectionTopAppBar(navController: NavController, isGroup: Boolean = false, modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)){
        Image(
            painter = if(isGroup) painterResource(id = R.drawable.group_collection_top_bar)
            else painterResource(id = R.drawable.collection_top_bar),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Row(modifier = Modifier) {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}


// 下面的這一個Compose的item要加上onclick 按下去要到指定的卡片
// navigate的時候可以傳cardViewModel.petCardData[id]進去
// 下面的items函示要多一個key, 可以參照codelab State in jetpack compose 的viewmodel那頁
@Composable
fun CollectionBody(
    navController: NavController,
    cardViewModel: CardViewModel,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
    ){
        items(cardViewModel.petCardData){ item ->
            CollectionCard(navController, petCard = item)
        }
    }
}


@Composable
fun CollectionCard(
    navController: NavController,
    petCard: PetCard,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 12.dp,
                    ),
                )
                .height(170.dp)
                .width(140.dp)
                .clickable(
                    enabled = true,
                    //onClick = {navController.navigate(Screen.RecordScreen.route)}
                    onClick = {
                        val json = Uri.encode(Gson().toJson(petCard))
                        navController.navigate("petCard/$json")
                    }
                )
            ,
            border = BorderStroke(4.dp, HexToJetpackColor.getColor(petCard.borderColor))
        )
        {
            Image(
                painter = if(petCard.available) painterResource(petCard.picture)
                else painterResource(petCard.shadow),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = if(petCard.available) petCard.name
            else "Unknown",
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun CardCollectionScreenPreview(){
    val navController = rememberNavController()
    val cardViewModel: CardViewModel = viewModel()
    CardCollectionScreen(navController, cardViewModel)
}

/*
@Preview
@Composable
fun CardCollectionTopAppBarPreview(){
    CardCollectionTopAppBar()
}*/

/*
@Preview
@Composable
fun CollectionCardPreview(){
    CollectionCard(
        PetCard(
        R.drawable.fish_card,
        R.drawable.fish_card_shadow,
        Color.Cyan,
        false,
        "Vaporeon"
    ))
}*/

object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}
