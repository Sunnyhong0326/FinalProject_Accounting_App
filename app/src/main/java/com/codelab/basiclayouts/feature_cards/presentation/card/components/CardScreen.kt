package com.codelab.basiclayouts.feature_cards.presentation.card.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
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
import com.codelab.basiclayouts.feature_cards.presentation.cardList
import com.codelab.basiclayouts.feature_account.domain.model.PetCard
import com.codelab.basiclayouts.feature_cards.presentation.CardViewModel
import com.codelab.basiclayouts.ui.theme.MySootheTheme

@Composable
fun CardScreen(
    navController: NavController,
    cardViewModel: CardViewModel = viewModel(),
    petCard: PetCard,
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
                    painter = painterResource(id = petCard.background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                CardScreenTopAppBar(navController)
                Spacer(modifier = Modifier.height(5.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(30.dp))
                    CardBody(
                        petCard
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    // RecordCardColumn(modifier)
                }

            }

        }
    }
}

@Composable
fun CardScreenTopAppBar(navController: NavController){
    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(330.dp))
        IconButton(onClick = { navController.navigateUp() }) { // Exit and back to card collection screen
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
fun CardBody(petCard: PetCard){
    val scroll = rememberScrollState(0)

    Card(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp,
                ),
            )
            .fillMaxSize()
            .padding(20.dp)
        , backgroundColor = Color.LightGray.copy(alpha=0.5f)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(20.dp))
            Text(text = if(petCard.available) petCard.name
            else "Unknown", modifier = Modifier, fontSize = 50.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Card(modifier = Modifier
                .width(210.dp)
                .height(250.dp)) {
                Image(
                    painter = if(petCard.available) painterResource(petCard.picture)
                    else painterResource(petCard.shadow),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier, horizontalArrangement = Arrangement.Center){
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "身高", modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${petCard.height}M", modifier = Modifier, fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "體重", modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${petCard.weight} KG", modifier = Modifier, fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "性別", modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = if(petCard.isMale) "Male" else "Female", modifier = Modifier, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "About", fontSize = 30.sp, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = petCard.description, fontSize = 15.sp, modifier = Modifier
                .width(250.dp)
                .verticalScroll(scroll),fontWeight = FontWeight.Bold
                , )
        }
    }
}

@Preview
@Composable
fun CardScreenPreview(){
    val navController = rememberNavController()
    CardScreen(navController, petCard = cardList[3])
}

object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}