package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.ui.theme.ImageAITheme

@Composable
fun ImageTextButton(modifier: Modifier = Modifier, text: String, imageId: Int= R.drawable.outline_chat_24, isDarkMode:Boolean=true, onClick:()->Unit){



    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(1.dp),
        colors = CardDefaults.cardColors( if (isDarkMode) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(30.dp),
    ) {
        Row(
            Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700, fontSize = 15.sp),
                textAlign = TextAlign.Center,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )


        }
    }
}


@Preview
@Composable
fun ImageTextButtonPreview(){
    ImageAITheme {
        ImageTextButton(text = "Sign in") {
            
        }
    }
}