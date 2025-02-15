package com.apps.aivision.ui.chats

import android.webkit.URLUtil
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apps.aivision.R
import com.apps.aivision.components.click
import com.apps.aivision.ui.theme.AIVisionTheme
import com.apps.aivision.ui.theme.OnSurfaceDark
import com.apps.aivision.ui.theme.White
import com.apps.aivision.ui.welcome.TextFieldError


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun URLInputDialog(onContinue:(String)->Unit, onCancel:()->Unit)
{
    var labelText by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = onCancel
    ) {

        Column(
            modifier = Modifier
                .heightIn(300.dp)
                /*.fillMaxHeight()
                .navigationBarsPadding()
                .imePadding()*/
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            /*.border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))*/,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(imageVector = Icons.Outlined.Close, contentDescription = null,
                Modifier
                    .align(Alignment.End)
                    .background(OnSurfaceDark, CircleShape)
                    .padding(3.dp)
                    .click {
                        onCancel()
                        //val file = Glide.with(contex).asFile().load(imageUri).submit().get()

                    })

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = labelText,
                onValueChange = {
                    labelText = it
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.image_url),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                    },
                textStyle = MaterialTheme.typography.bodyLarge,
                  keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            )
            if (isError) {
                TextFieldError(textError = stringResource(id = R.string.url_error))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable {

                        if (URLUtil
                                .isValidUrl(labelText)
                                .not()
                        ) {
                            isError = true
                            return@clickable
                        } else {
                            isError = false
                        }
                        onContinue(labelText)
                    },
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.user_continue),
                        color = MaterialTheme.colorScheme.onBackground,
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
fun URLInputPreview(){
    AIVisionTheme {
        URLInputDialog(onContinue = {}) {

        }
    }
}