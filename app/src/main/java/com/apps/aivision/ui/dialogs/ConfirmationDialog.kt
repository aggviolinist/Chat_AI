package com.apps.aivision.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.apps.aivision.R
import com.apps.aivision.ui.theme.AIVisionTheme


@Composable
fun ConfirmationDialog(title:String,message:String,onCancel:()->Unit,onConfirmed:()->Unit)
{

    Dialog(
        onDismissRequest = onCancel
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(25.dp))
                .border(1.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(25.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W700),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            Divider(
                color = MaterialTheme.colorScheme.tertiary,
                thickness = 1.dp,
                modifier = Modifier.padding(5.dp)
            )

            Text(
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .alpha(0.8f)
            )

            Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(90.dp))
                        .clickable {
                            onCancel()
                        },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(90.dp),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                            textAlign = TextAlign.Center
                        )

                    }
                }

                Spacer(modifier = Modifier.width(20.dp))
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .clickable {
                            onConfirmed()
                        },
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(90.dp),
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.yes),
                           /* color = White,*/
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmDialogPreview(){
    AIVisionTheme {
        ConfirmationDialog(title = "Warning", message = "Are you sure?", onCancel = {  }) {

        }
    }
}