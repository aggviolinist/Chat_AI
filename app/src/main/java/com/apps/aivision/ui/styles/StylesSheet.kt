package com.apps.aivision.ui.styles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.aivision.R
import com.apps.aivision.components.click
import com.apps.aivision.data.model.StyleModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StylesSheet(selectedId:String,showSheet:Boolean,onDismiss:()->Unit, onSelected: (StyleModel) -> Unit, viewModel: StyleViewModel= hiltViewModel())
{

    val stylesList by viewModel.styles.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false)
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
                    .background(MaterialTheme.colorScheme.onSecondary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.select_style),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )

                LazyVerticalGrid(modifier = Modifier.padding(top = 5.dp, bottom = 50.dp),
                    columns = GridCells.Fixed(3), contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(stylesList) { photo ->
                        ItemCard(photo,photo.id.contentEquals(selectedId),onSelected)
                    }
                }

            }

        }
    }
}

@Composable
fun ItemCard(model:StyleModel,isSelected:Boolean,onSelected: (StyleModel) -> Unit)
{
    val modifier = if (isSelected) Modifier
        .padding(3.5.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .aspectRatio(ratio = 1f)
        .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
    else
        Modifier
            .padding(3.5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(ratio = 1f)
    val isNoFilter = model.id.contentEquals("none")

    Box (modifier.click { onSelected(model) })
    {
        var colorFilter: ColorFilter?=null
        if (isSelected && isNoFilter)
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

        Image(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onTertiary, shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = model.resourceId), contentDescription ="Style Image",
            contentScale = if (isNoFilter) FixedScale(1.8f) else ContentScale.Crop,
            colorFilter = colorFilter)

        Text(
            text = model.name.uppercase(),
            color = if (isNoFilter) MaterialTheme.colorScheme.onBackground else Color.White,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        )
        if (isSelected && isNoFilter.not())
        {
            Image(modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.baseline_check_24), contentDescription ="Style Image",
                contentScale = FixedScale(1.8f)
            )
        }
    }
}