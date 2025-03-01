package com.apps.imageAI.ui.subscription

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.imageAI.R
import com.apps.imageAI.components.InAppPurchaseHelper
import com.apps.imageAI.components.PurchaseStatus
import com.apps.imageAI.components.Utils
import com.apps.imageAI.components.click
import com.apps.imageAI.data.model.CreditModel
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.theme.CreditsTint
import kotlinx.coroutines.delay


@Composable
fun SubscriptionScreen(inAppPurchaseHelper: InAppPurchaseHelper, navigateBack:()->Unit,subscriptionViewModel: SubscriptionViewModel = hiltViewModel())
{
    val bundleList by inAppPurchaseHelper.bundles.collectAsState()
    val isPaymentProcessing by inAppPurchaseHelper.processingPurchase.collectAsState()
    val purchaseFlowStatus by inAppPurchaseHelper.purchaseStatus.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(purchaseFlowStatus) {

        if (purchaseFlowStatus== PurchaseStatus.Success)
        {
            Toast.makeText(context,context.getString(R.string.welcome_pre),Toast.LENGTH_LONG).show()
            navigateBack()
        }
    }

    LaunchedEffect(Unit){
        val currentLanguageCode = subscriptionViewModel.getCurrentLanguageCode()
        Utils.changeLanguage(context, currentLanguageCode)
        Utils.changeLanguage(context.applicationContext, currentLanguageCode)
    }

    SubscriptionScreenUI(
        bundleList = bundleList,
        isPaymentProcessing = isPaymentProcessing,
        navigateBack = { navigateBack() },
        onContinueClick = {
            inAppPurchaseHelper.makePurchase(it)
        }
    )
}


@Composable
fun SubscriptionScreenUI(bundleList:List<CreditModel>,isPaymentProcessing:Boolean,navigateBack:()->Unit,onContinueClick:(String)->Unit)
{
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    var isCrossVisble by remember {
        mutableStateOf(false)
    }
    var isClickHanlde by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        //inAppPurchaseHelper.fetchProductsDetails()
        delay(1500)
        isCrossVisble = true
    }

    Box (
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 12.dp)){
        if (isCrossVisble) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(30.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        color = MaterialTheme.colorScheme.onTertiary,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .clip(RoundedCornerShape(90.dp))
                    .clickable {

                        if (isClickHanlde.not()) {
                            isClickHanlde = true
                            navigateBack()
                        }
                    }
                    .padding(5.dp)
                    )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(top = 12.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,

            )
        {

            Spacer(modifier = Modifier.height(20.dp))

            Row {

                Text(
                    text = stringResource(R.string.sub_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W700),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painterResource(id = R.drawable.ic_crown),
                    contentDescription = "",
                    tint = CreditsTint,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.gpt_4_access),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlimited_image),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlimited_messages),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlimited_words),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_ads),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            if (bundleList.size>2) {

                Column(
                    modifier = Modifier
                        .click {
                            selectedIndex = 0
                        }
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = if (selectedIndex == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary
                        )
                        .border(
                            1.dp,
                            color = if (selectedIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(
                        text = stringResource(R.string.weekly),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = bundleList[0].price,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center,
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .click {
                            selectedIndex = 1
                        }
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = if (selectedIndex == 1) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary
                        )
                        .border(
                            1.dp,
                            color = if (selectedIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(
                        text = stringResource(R.string.monthly),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = bundleList[1].price,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center,
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .click {
                            selectedIndex = 2
                        }
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = if (selectedIndex == 2) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary
                        )
                        .border(
                            1.dp,
                            color = if (selectedIndex == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(
                        text = stringResource(R.string.yearly),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = bundleList[2].price,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Center,
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable(onClick = {
                            if (selectedIndex <= bundleList.size) {
                                onContinueClick(bundleList[selectedIndex].bundleId)
                            }
                        }),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {

                        Text(
                            text = stringResource(id = R.string.user_continue),
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = Barlow
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )


                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.cancel_billing),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.W500),
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(12.dp))
                if (isPaymentProcessing) {
                    CircularProgressIndicator(modifier = Modifier
                        .then(Modifier.size(32.dp))
                        .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary)
                }
            } else {
                Text(
                    text = stringResource(id = R.string.products_not_found),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.W500),
                    textAlign = TextAlign.Center

                )
            }
        }

    }
}

@Preview
@Composable
fun SubPreview(){
    ImageAITheme {
        SubscriptionScreenUI(mutableListOf(CreditModel(1,50,"$5","1","Weekly"),CreditModel(2,50,"$19","2","Monthly"),CreditModel(3,50,"$200","3","Yearly")),false, navigateBack = {}){}
    }
}
