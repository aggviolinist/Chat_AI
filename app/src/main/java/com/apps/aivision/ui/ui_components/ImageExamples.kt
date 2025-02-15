package com.apps.aivision.ui.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivision.R
import com.apps.aivision.data.model.ImageExample
import com.apps.aivision.ui.theme.AIVisionTheme
import com.apps.aivision.ui.theme.Barlow

@Composable
fun ImageExamples(
    modifier: Modifier = Modifier,
    inputText: String, onInput: (String)->Unit
) {

    val data = mutableListOf(
        ImageExample(R.drawable.image_1, stringResource(id = R.string.image_inp_1)),ImageExample(R.drawable.image_2,stringResource(id = R.string.image_inp_2)),ImageExample(R.drawable.image_3,stringResource(id = R.string.image_inp_3)),ImageExample(R.drawable.image_4,stringResource(id = R.string.image_inp_4)),ImageExample(R.drawable.image_5,stringResource(id = R.string.image_inp_5)),ImageExample(R.drawable.image_6,stringResource(id = R.string.image_inp_6)),ImageExample(R.drawable.image_7,stringResource(id = R.string.image_inp_7)),ImageExample(R.drawable.image_8,stringResource(id = R.string.image_inp_8))
        ,ImageExample(R.drawable.image_9,stringResource(id = R.string.image_inp_9)),ImageExample(R.drawable.image_10,stringResource(id = R.string.image_inp_10)))

    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

         Row {
             Icon(imageVector =Icons.Default.LightMode , contentDescription ="",modifier=Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface )
             Spacer(modifier = Modifier.width(8.dp))
          Text(
                text = inputText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.W700, platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                textAlign = TextAlign.Center
            )
         }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                items(data)
                {
                    ImageItem(item = it,onInput)
                }
            }



        }
    }
}


@Composable
fun ImageItem(item:ImageExample,onTry:(String)->Unit)
{
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
            horizontalAlignment = Alignment.Start,

        ) {
            Box {
            Image(
                painter = painterResource(item.resId),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3.6f)
                    .clip(
                        RoundedCornerShape(
                            15.dp
                        )
                    )
                    .clickable(
                        onClick = {
                            onTry(item.text)
                        }),
                contentScale = ContentScale.Crop
            )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .align(Alignment.BottomEnd)
                        ,
                    text = stringResource(id = R.string.image_try),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Barlow
                    ),
                    textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis,

                    )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier.fillMaxWidth(),
                text = item.text,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = Barlow
                ), textAlign = TextAlign.Center, maxLines = 2,overflow = TextOverflow.Ellipsis
            )
        }

}

@Preview
@Composable
fun imexemplePreview(){
    AIVisionTheme {
ImageExamples( inputText = "Examples", onInput = {})
    }
}