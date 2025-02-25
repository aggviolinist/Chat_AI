package com.apps.aivision.ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivision.R
import com.apps.aivision.components.click
import com.apps.aivision.ui.theme.AIVisionTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaSourceBottomSheet(showSheet:Boolean,onCameraAction:()->Unit,onGalleryAction:()->Unit,onLink:()->Unit,onDismiss:()->Unit)
{

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false)
    if (showSheet) {

        ModalBottomSheet(
            modifier = Modifier, sheetState = sheetState, onDismissRequest = {
                onDismiss()
            }, shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp
            ),dragHandle = {
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(90.dp))
                )
            }, containerColor = MaterialTheme.colorScheme.onSecondary
        ){
            MediaSourceUI(onCameraAction = onCameraAction, onGalleryAction = onGalleryAction,onLink=onLink)
        }
    }
}

@Composable
fun MediaSourceUI(onCameraAction:()->Unit,onGalleryAction:()->Unit,onLink:()->Unit){

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
        /*.border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))*/,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .width(25.dp)
                .height(3.dp)
                .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(90.dp))
        )*/

        Text(
            text = stringResource(R.string.select_media_source),
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontSize = 15.5.sp,
                fontWeight = FontWeight.W600,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )


        Row (
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 60.dp),horizontalArrangement = Arrangement.SpaceEvenly){
            Column (modifier = Modifier.click { onCameraAction() },horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription ="Camera Icon"
                    ,    tint = MaterialTheme.colorScheme.onBackground)
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = stringResource(R.string.camera),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                    ),
                    textAlign = TextAlign.Center,

                    )
            }

            HorizontalDivider(
                Modifier
                    .width(1.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Column (modifier = Modifier.click { onGalleryAction() }, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Outlined.Image, contentDescription ="Gallery Icon"
                    ,    tint = MaterialTheme.colorScheme.onBackground)
                Text( modifier = Modifier.padding(top = 5.dp),
                    text = stringResource(R.string.gallery),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                    ),
                    textAlign = TextAlign.Center,

                    )
            }

            HorizontalDivider(
                Modifier
                    .width(1.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Column (modifier = Modifier.click { onLink() }, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Outlined.Link, contentDescription ="Link Icon"
                    ,    tint = MaterialTheme.colorScheme.onBackground)
                Text( modifier = Modifier.padding(top = 5.dp),
                    text = stringResource(R.string.link),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                    ),
                    textAlign = TextAlign.Center,

                    )
            }

        }

    }
}

@Preview
@Composable
fun MediaSourcePreview(){
    AIVisionTheme {
        MediaSourceUI(onCameraAction = { /*TODO*/ }, onGalleryAction = {}) {

        }
    }
}