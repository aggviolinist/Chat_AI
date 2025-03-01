package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.theme.White

@Composable
fun ChipItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) White else MaterialTheme.colorScheme.primary,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = Barlow,
            lineHeight = 25.sp
        ), modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(90.dp))
            .clickable (onClick = {
                onClick()
            })
            .background(
                shape = RoundedCornerShape(90.dp),
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
            )
            .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(90.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp)
    )


}
