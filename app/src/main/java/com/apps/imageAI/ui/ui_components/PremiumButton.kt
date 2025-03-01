package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.CreditsTint


@Composable
fun PremiumButton(modifier: Modifier= Modifier,onClick:()->Unit){

    //Spacer(modifier = Modifier.width(10.dp))
    Box (modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() }

                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 12.dp, horizontal = 12.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.ic_crown),
                contentDescription = stringResource(R.string.app_name),
                tint = CreditsTint,
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    /*.background(MaterialTheme.colors.primary, Polygon(5,55f))
                    .padding(12.dp)*/
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (Modifier.weight(1f)) {

            Text(
                text = stringResource(id = R.string.pro_title),
                color =  MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W700 ))
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.pro_sub_title),
                    color =  MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500, fontSize = 12.sp),
                    modifier=Modifier.padding(start = 5.dp)
                )
            }

        }

    }
}

@Preview
@Composable
fun PremiumPreview(){
    ImageAITheme {
        PremiumButton {

        }
    }
}