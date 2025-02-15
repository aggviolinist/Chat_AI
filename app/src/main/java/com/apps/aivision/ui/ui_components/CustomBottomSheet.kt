package com.apps.aivision.ui.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivision.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(onDismiss:()->Unit) {

    ModalBottomSheet(modifier = Modifier, sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false), onDismissRequest = { onDismiss()
    }, shape = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 10.dp
    ), dragHandle = null
    ) {
        CustomBottomSheetContainer()
    }
}


@Composable
fun CustomBottomSheetContainer() {
    Scaffold(topBar = {
        Column {
            Text(text = "Theme", modifier = Modifier.height(75.dp)
                .padding(start = 29.dp, top = 26.dp),fontSize = 23.sp)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Select theme", modifier = Modifier
                .padding(start = 29.dp, top = 20.dp, bottom = 10.dp)
                .height(40.dp),fontSize = 20.sp)
            CustomItem("Light")
            CustomItem("Dark")
            CustomItem("System default")
        }
    }
}

@Composable
fun CustomItem(text: String) {
    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_crown),
            modifier = Modifier.padding(start = 31.dp, top = 9.dp), contentDescription = ""
        )
        Text(
            text = text, modifier = Modifier
                .height(40.dp)
                .padding(start = 20.dp, top = 11.dp),
            fontSize = 18.sp
        )
    }
}