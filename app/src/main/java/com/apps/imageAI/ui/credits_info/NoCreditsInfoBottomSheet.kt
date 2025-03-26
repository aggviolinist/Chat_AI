package com.apps.imageAI.ui.credits_info

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.imageAI.R
import com.apps.imageAI.components.loadRewarded
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.CreditsTint
import com.apps.imageAI.ui.ui_components.AdsButton
import com.apps.imageAI.ui.ui_components.NoCreditsErrorMessage
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoCreditsInfoBottomSheet(showSheet:Boolean,minCreditsRequired:Int,onDismiss:()->Unit,onUpgrade:()->Unit,viewModel:NoCreditsInfoViewModel = hiltViewModel())
{
    val focusManager = LocalFocusManager.current
    focusManager.clearFocus(true)
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()
    val creditsCount by viewModel.creditsCount.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false)
if (showSheet) {
    ModalBottomSheet(
        modifier = Modifier, sheetState = sheetState, onDismissRequest = {
            onDismiss()
        }, shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        ), dragHandle = null
    ) {
        NoCreditsInfoUI(
            modifier = Modifier,
            minCreditsRequired = minCreditsRequired,
            creditsCount,
            onCross = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismiss()
                    }
                }

            },
            onUpgrade = onUpgrade,
            onGiveReward = { viewModel.giveAdReward() })
    }
}
}

@Composable
fun NoCreditsInfoUI(modifier: Modifier,minCreditsRequired:Int,creditCount:Int,onCross:()->Unit,onUpgrade:()->Unit,onGiveReward:()->Unit){

    var isAdLoading by remember { mutableStateOf(false) }
    val isSubMode = true
    val context = LocalContext.current
    BackHandler {
        onCross()
    }

    Column( modifier = Modifier
        .background(MaterialTheme.colorScheme.onSecondary)
        .padding(12.dp)
        .padding(bottom = 54.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Box(Modifier.fillMaxWidth()) {

            IconButton(
                onClick = { onCross() },
                modifier = Modifier.size(25.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "image",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
            Text(
                text = stringResource(R.string.free_usage),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W600),
                textAlign = TextAlign.Center,
                modifier=modifier.align(Alignment.TopCenter)
            )

            Row (modifier = modifier.align(Alignment.TopEnd)){
                Icon(
                    painter = painterResource(R.drawable.outline_credit),
                    contentDescription = "image",
                    tint = CreditsTint,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                        .padding(end = 3.dp)
                )
                Text(
                    text = " $creditCount",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W600),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        NoCreditsErrorMessage(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(), minCreditsRequired
        )

        Row(modifier=Modifier.fillMaxWidth()) {

            if (isSubMode) {
                AdsButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    resourceId = R.drawable.ic_crown,
                    buttonText = stringResource(id = R.string.upgrade_premium),
                    isAdLoading = false,
                    showCreditText = false
                ) {

                    onUpgrade()
                }
            }

        AdsButton(modifier = Modifier
            .weight(1f)
            .padding(horizontal = if (isSubMode) 5.dp else 50.dp, vertical = 5.dp), resourceId = R.drawable.video, buttonText = stringResource(id = R.string.watch_short_ad),isAdLoading= isAdLoading,showCreditText = false) {
            if (isAdLoading.not()) {
                isAdLoading = true
//                loadRewarded(context, { errorMsg ->
//                    isAdLoading = false
//                }, {
//                    onGiveReward()
//                })
                loadRewarded(context, { errorMsg ->
                    if (errorMsg != null) {
                        Log.d("TAG", "Error loading ad: $errorMsg")
                        // Optionally, display an error message to the user
                        Toast.makeText(context, "Error loading ad: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                    isAdLoading = false
                }, {
                    onGiveReward()
                })

            }
        }

        }
    }
}

@Preview
@Composable
fun NoCreditsPreview(){
    ImageAITheme {
        NoCreditsInfoUI(modifier = Modifier, minCreditsRequired = 2, creditCount = 1, onCross = {}, onUpgrade = {}) {

        }
    }
}