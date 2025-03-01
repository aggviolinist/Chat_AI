package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.components.click
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Barlow


@Composable
fun TopBarSearch(onSearchText:(String)->Unit, onBackClick:()-> Unit)
{
    var text by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {
               onBackClick()
            },
            modifier = Modifier
                .width(27.dp)
                .height(27.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "image",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .width(27.dp)
                    .height(27.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onSearchText(it)
            },
            label = null,
            placeholder = {
                Text(
                    stringResource(R.string.search_conversation),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Barlow,
                    fontWeight = FontWeight.W500
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.W600),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                /*disabledIndicatorColor = Color.Transparent,*/
                unfocusedContainerColor=MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary
            ),
            trailingIcon = {

                if (text.isNotEmpty()){
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "image",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                        .click {
                            text = ""
                            onSearchText("")
                        }
                )
                }
            }
            ,
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 50.dp)
                .heightIn(max = 50.dp)
                .weight(1f)
                .border(
                    1.dp,
                    if (hasFocus) MaterialTheme.colorScheme.secondary else Color.Transparent,
                    RoundedCornerShape(25.dp)
                )
                .onFocusChanged { focusState -> hasFocus = focusState.hasFocus }
        )
    }
}

@Preview
@Composable
fun SearchPreview(){
    ImageAITheme {
        TopBarSearch(onSearchText = {}) {
            
        }
    }
}