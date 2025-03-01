package com.apps.imageAI.ui.chats

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.components.Utils
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.theme.White
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailSheet(mediaPath:String,showSheet:Boolean,onDismiss:()->Unit)
{

    var isDownloaded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true)
    if (showSheet)
    {
        ModalBottomSheet(
            modifier = Modifier, sheetState = sheetState, onDismissRequest = {
                onDismiss()
            }, shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp
            ), dragHandle = {
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(90.dp))
                )
            }, containerColor = MaterialTheme.colorScheme.onSecondary
        ){

            Column(
                modifier = Modifier
                    .padding(12.dp)/*.border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))*/,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.image_detail),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(12.dp),
                    thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary
                )

                GlideImage(
                    imageModel = { mediaPath },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        /* .padding(2.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))*/
                        .aspectRatio(ratio = 1f / 1f)
                    ,
                )

                /* Image(painter = painterResource(id = R.drawable.fantasy_art), contentDescription =null,
                     modifier = Modifier
                         .fillMaxWidth()
                         *//*.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))*//*
                .aspectRatio(ratio = 1f / 1f) )*/

                Row(modifier = Modifier.padding(top = 20.dp, bottom = 50.dp/*, start = 16.dp, end = 16.dp*/)) {
                    Card(
                        modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(90.dp))
                            .clickable {
                                onDismiss()
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
                                text = stringResource(R.string.cancel),
                                color = MaterialTheme.colorScheme.primary,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = Barlow
                                ),
                                textAlign = TextAlign.Center
                            )

                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp)
                            .clip(RoundedCornerShape(90.dp))
                            .clickable {
                                // download code
                                isDownloaded = true
                                CoroutineScope(Dispatchers.Default).launch {

                                    CoroutineScope(Dispatchers.Main).launch {

                                        Toast.makeText(context,context.resources.getString(R.string.save_to_gallery),
                                            Toast.LENGTH_LONG).show()
                                    }

                                    runCatching {
                                        Utils.copyFileIntoGallery(Uri.parse(mediaPath), context = context)
                                    }.onFailure { it.printStackTrace() }

                                }
                            },
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(90.dp),
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.download),
                                color = White,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = Barlow
                                ),
                                textAlign = TextAlign.Center
                            )

                        }
                    }
                }


            }

        }
    }
}

