package com.apps.imageAI.ui.ui_components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.components.bounceClick
import com.apps.imageAI.components.click
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.theme.CreditsTint
import com.apps.imageAI.ui.theme.OnSurfaceDark
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ImageInputCard(modifier: Modifier = Modifier,imageUri: Uri,isPremium:Boolean,isLoadingError:MutableState<Boolean>,onPromptSelected:(String)->Unit,onCancel:()->Unit)
{
    Box (modifier = modifier/*.background(Color.Black.copy(0.1f))*/) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )

            ) {


                GlideImage(
                    imageModel = { imageUri },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .aspectRatio(ratio = 4f / 3f)
                    , loading = {
                        isLoadingError.value = false
                        Box(modifier = modifier.fillMaxSize()){
                        CircularProgressIndicator(modifier = Modifier
                            .then(Modifier.size(50.dp))
                            .align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary)
                        }
                    }, failure = {
                        isLoadingError.value = true
                        Box(modifier = modifier.fillMaxSize()){
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = stringResource(R.string.app_name),
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                )

               /* Text(
                    text = stringResource(R.string.image_input_prompt),
                    color = MaterialTheme.colors.onSurface,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Barlow,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )*/


                val p1 = stringResource(id = R.string.image_input_p1)
                Text(
                    text = p1,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Barlow,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center
                    ,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(20)
                        )
                        .bounceClick(
                            onClick = {
                                onPromptSelected(p1)
                            })

                        .padding(10.dp)
                        .fillMaxWidth()

                )

                val p2 = stringResource(id = R.string.image_input_p2)
              /*  Text(
                    text = p2,
                    color = MaterialTheme.colors.onSurface,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Barlow,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(20)
                        )
                        .bounceClick(
                            onClick = {
                                onPromptSelected(p2)
                            })
                        .padding(10.dp)
                        .fillMaxWidth()

                )*/

                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .padding(5.dp)
                    .bounceClick(
                        onClick = {
                            onPromptSelected(p2)
                        })
                    .background(
                        color = MaterialTheme.colorScheme.onTertiary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
                    .fillMaxWidth()) {
                    Text(
                        text = p2,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = Barlow,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Start, modifier = Modifier.weight(1.0f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                   /* if (isPremium.not()){
                    Icon(
                        painter = painterResource(R.drawable.ic_crown),
                        contentDescription = stringResource(R.string.app_name),
                        tint = CreditsTint,
                        modifier = Modifier
                            .size(width = 20.dp, height = 20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    }*/
                }

                val p3 = stringResource(id = R.string.image_input_p3)
                Text(
                    text = p3,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Barlow,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(20)
                        )
                        .bounceClick(
                            onClick = {
                                onPromptSelected(p3)
                            })

                        .padding(10.dp)
                        .fillMaxWidth()

                )

                val p4 = stringResource(id = R.string.image_input_p4)
                Text(
                    text = p4,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Barlow,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(20)
                        )
                        .bounceClick(
                            onClick = {
                                onPromptSelected(p4)
                            })

                        .padding(10.dp)
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(10.dp))
            }

          //  val contex = LocalContext.current

            Image(imageVector = Icons.Outlined.Close, contentDescription = null,
                Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .background(OnSurfaceDark, CircleShape)
                    .padding(3.dp)
                    .click {
                        //val file = Glide.with(contex).asFile().load(imageUri).submit().get()
                        onCancel()
                    })
        }
    }
}


@Preview
@Composable
fun ImageInputPreview(){
    ImageAITheme {
    //ImageInputCard(imageUri = Uri.parse(""), onPromptSelected ={}, onCancel = {} )
    }
}