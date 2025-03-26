package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apps.imageAI.R

@Composable
fun Examples(
    modifier: Modifier = Modifier,
    examples: List<String>,
    image: Int? = null,
    inputText: String,
    onInput: (String) -> Unit,
    onAssistants: () -> Unit
) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onAssistants() }
                    .background(
                        color = Color(0xFF6495ED), // Blue background
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                if (image != null) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(28.dp)
                            .padding(3.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LightMode,
                        contentDescription = "",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFFADD8E6) // Light blue text color
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = inputText,
                    color = Color(0xFFADD8E6), // Light blue text color
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.W700,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp),
                    tint = Color(0xFFADD8E6) // Light blue text color
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(examples) { index, example ->
                    val backgroundColor = when (index % 3) {
                        0 -> Color(0xFF808080).copy(alpha = 0.05f) // Very light grey background
                        1 -> Color(0xFFFFA07A).copy(alpha = 0.05f) // Very light orange background
                        else -> Color(0xFF00695C).copy(alpha = 0.05f) // Very light brown background
                    }
                    val textColor = when (index % 3) {
                        0 -> Color(0xFF808080) // Grey text
                        1 -> Color(0xFFFFA07A) // Orange text
                        else -> Color(0xFF00695C) // Brown text
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(
                                onClick = {
                                    onInput(example.replace("\n", ""))
                                }
                            )
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = example,
                            color = textColor,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold // Make text bold
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Send,
                            contentDescription = "",
                            tint = textColor
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
