package com.apps.aivision.ui.ui_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivision.ui.theme.Barlow
import com.apps.aivision.R

@Composable
fun PromptCard(
    image: Int,
    name: String,
    description: String,
    onClick: () -> Unit
) {

    Box(modifier = Modifier
        .padding(6.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable (onClick = onClick)
        .size(width = 170.dp, height = 210.dp)
        .border(1.dp, color = MaterialTheme.colorScheme.onTertiary, shape = RoundedCornerShape(16.dp))
         ){
        Surface (modifier = Modifier.padding(1.dp).fillMaxWidth().height(80.dp).alpha(0.7f),color=MaterialTheme.colorScheme.onTertiary, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)){}

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(10.dp)

    ) {
        Image(
            painter = painterResource(image),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(width = 60.dp, height = 60.dp)
                .padding(5.dp).align(alignment = Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                fontFamily = Barlow,
                lineHeight = 25.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = Barlow,
                lineHeight = 20.sp
            )
        )
    }
}
}


