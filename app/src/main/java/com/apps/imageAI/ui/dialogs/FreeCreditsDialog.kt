package com.apps.imageAI.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.apps.imageAI.R
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.theme.CreditsTint


@Composable
fun FreeCreditsDialog(creditsCount:Int=3,onDismiss:()->Unit)
{
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(35.dp))
                .border(1.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(35.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.congrats),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.W600, fontFamily = Barlow),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.tertiary
            )

            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.outline_credit),
                    contentDescription = "Credits",
                    colorFilter = ColorFilter.tint(CreditsTint)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = String.format("%02d", creditsCount),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onBackground, fontFamily = Barlow)
                )
            }


            Text(
                text = stringResource(R.string.daily_free_credits),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W600, fontFamily = Barlow, fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )


                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .widthIn(150.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .clickable {
                            onDismiss()
                        },
                    elevation = CardDefaults.elevatedCardElevation(0.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(90.dp),
                ) {
                    Row(
                        Modifier.weight(1f).widthIn(150.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                            textAlign = TextAlign.Center
                        )

                    }


            }
        }
    }
}


@Preview
@Composable
fun DialogPreview(){
    ImageAITheme {
        FreeCreditsDialog {

        }
    }
}