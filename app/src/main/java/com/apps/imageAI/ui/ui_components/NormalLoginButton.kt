package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Barlow

@Composable
fun NormalLoginButton(modifier: Modifier = Modifier, text: String,onClick:()->Unit){



    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable(onClick = {
                onClick()
            }),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
       /* border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),*/
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {

            Image( imageVector = Icons.Default.AccountCircle , colorFilter = ColorFilter.tint(Color.White), contentDescription ="login", modifier = Modifier.size(50.dp).padding(start = 16.dp).align(Alignment.CenterStart) )
            Text(
                text = text,
                color = Color.White,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Barlow
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )


        }
    }
}


@Preview
@Composable
fun NormalButtonPreview(){
    ImageAITheme {
        NormalLoginButton(text = "Signin") {
            
        }
    }
}