package com.apps.aivision.ui.drawer

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.aivision.BuildConfig
import com.apps.aivision.R
import com.apps.aivision.components.Constants
import com.apps.aivision.components.InAppPurchaseHelper
import com.apps.aivision.components.SignInType
import com.apps.aivision.data.model.GPTModel
import com.apps.aivision.ui.MainActivityViewModel
import com.apps.aivision.ui.dialogs.ConfirmationDialog
import com.apps.aivision.ui.dialogs.DeleteAccountDialog
import com.apps.aivision.ui.theme.Barlow
import com.apps.aivision.ui.ui_components.PremiumButton
import com.apps.aivision.ui.ui_components.RowToggleButtonGroup

@Composable
fun AppDrawerContent(navigateLanguages: () -> Unit, navigateSubscription: () -> Unit, onLogout: ()->Unit, onCloseAction: ()->Unit, inAppPurchaseHelper: InAppPurchaseHelper,
                     drawerViewModel:DrawerViewModel = hiltViewModel(), @SuppressLint("ContextCastToActivity") mainActivityViewModel: MainActivityViewModel = hiltViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
){
    val darkTheme by mainActivityViewModel.darkMode.collectAsState()
    val gptModel by drawerViewModel.currentGptModel.collectAsState()
    val isCreditsPurchased by drawerViewModel.isCreditsPurchased.collectAsState()
    val shouldUpdate by mainActivityViewModel.shouldDrawerUpdate.collectAsState()
    val isImageGeneration by mainActivityViewModel.isImageGeneration.collectAsState()
    var isGuest by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        drawerViewModel.getCurrentLanguage()
        drawerViewModel.getCurrentGptModel()
    }
    LaunchedEffect(shouldUpdate){
        isGuest = mainActivityViewModel.isGuestMode()
        mainActivityViewModel.getCurrentLanguageCode()
        drawerViewModel.getCurrentLanguage()
    }
    val context = LocalContext.current
    var toast by remember {
        mutableStateOf("")
    }

    if (toast.isNotEmpty())
    {
        Toast.makeText(LocalContext.current,toast, Toast.LENGTH_LONG).show()
        toast =""
    }
    val language by drawerViewModel.currentLanguage.collectAsState()

    ModalDrawerSheet(modifier = Modifier, drawerContainerColor = MaterialTheme.colorScheme.background, drawerShape = RoundedCornerShape(1.dp)) {
        SettingsUI(language,
            isSubscribed = isCreditsPurchased,
            darkMode = darkTheme,isGuest,
            navigateSubscription = { navigateSubscription()},
            onLogout = {onLogout()},
            gptModel = gptModel,
            onGptModel = { drawerViewModel.setGptModel(it)}, onRestore = {inAppPurchaseHelper.restorePurchase { toast = if (it) {
                context.getString( R.string.welcome_pre)} else {
                context.getString( R.string.premium_not)} }},
            onDarkMode = {
                mainActivityViewModel.setDarkMode(it)
            },onCloseAction, isImageGen = isImageGeneration, onImageGeneration = {mainActivityViewModel.enableImageGenerations(it)}
        , navigateLanguages = {navigateLanguages()})
    }

}


@Composable
fun SettingsUI(language:String,isSubscribed:Boolean, darkMode: Boolean,isGuest:Boolean, navigateSubscription: () -> Unit,onLogout: () -> Unit, gptModel: GPTModel, onGptModel: (GPTModel) ->Unit, onRestore:()->Unit, onDarkMode : (Boolean) ->Unit,onCloseAction: ()->Unit,isImageGen:Boolean,
               onImageGeneration: (Boolean) -> Unit,navigateLanguages:()->Unit)
{
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val isSubMode = true
    var logoutDialog by remember { mutableStateOf(false) }
    var deleteAccountDialog by remember { mutableStateOf(false) }

    if (logoutDialog)
    {
        ConfirmationDialog(title = stringResource(R.string.confirmation), message = stringResource(id= if (isGuest.not())R.string.are_you_sure_logout else R.string.are_you_sure_exit), onCancel = {
            logoutDialog = false
        }) {
            logoutDialog=false
            onCloseAction()
            onLogout()
        }
    }

    if (deleteAccountDialog)
    {
        DeleteAccountDialog( onCancel = {
            deleteAccountDialog = false
        }, onConfirmed = {
            deleteAccountDialog = false
            onCloseAction()
            onLogout()
        })
    }

    Column(Modifier.fillMaxSize().verticalScroll(
        rememberScrollState()
    )) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
                .padding(horizontal = 16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.app_icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.app_name),
                /*  color =  MaterialTheme.colorScheme.onSurface,*/
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W700)
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    /*.padding(16.dp)*/
                    .size(30.dp)
                    .align(Alignment.Top)
                    .background(
                        color = MaterialTheme.colorScheme.onTertiary,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .clip(RoundedCornerShape(90.dp))
                    .clickable {
                        onCloseAction()
                    }
                    .padding(5.dp)
            )
        }

        if (isSubMode && isSubscribed.not()) {
            PremiumButton(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                onCloseAction()
                navigateSubscription()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_chat_24),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.default_model),
                /*color = MaterialTheme.colorScheme.surface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))

            val selection = if (gptModel == GPTModel.GPT_4) 1 else 0
            RowToggleButtonGroup(
                modifier = Modifier.height(30.dp),
                primarySelection = selection,
                buttonCount = 2,
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.background,
                selectedContentColor = Color.White,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                elevation = ButtonDefaults.buttonElevation(0.dp), // elevation of toggle group buttons
                buttonTexts = arrayOf(
                    "GPT-4o-mini", " GPT-4o "
                ),
            ) { index ->
                if (isSubMode && isSubscribed.not()) {
                    onCloseAction()
                    navigateSubscription()
                    return@RowToggleButtonGroup
                }
                onGptModel(if (index == 1) GPTModel.GPT_4 else GPTModel.GPT_3_5_TURBO)
            }


        }
        /*  Divider( modifier = Modifier
            .padding(start = 63.dp),
            color = MaterialTheme.colorScheme.tertiary,
        )*/

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onCloseAction()
                    navigateLanguages()
                }
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Language,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.language),
                /*  color = MaterialTheme.colorScheme.surface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = language,
                /* color = MaterialTheme.colorScheme.surface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Barlow,
                    lineHeight = 25.sp
                )
            )

            Spacer(modifier = Modifier.width(15.dp))
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.DarkMode,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.dark_theme),
                /*color = MaterialTheme.colorScheme.surface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))

            var switchOnCurrent by remember {
                mutableStateOf(darkMode)
            }
            Switch(
                modifier = Modifier.height(30.dp), checked = switchOnCurrent, onCheckedChange = {
                    onDarkMode(it)
                    switchOnCurrent = !switchOnCurrent
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    uncheckedTrackColor = MaterialTheme.colorScheme.onTertiary,
                )

            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Image,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.image_content),
                /*color = MaterialTheme.colors.surface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))

            Switch(
                checked = isImageGen, onCheckedChange = {
                    onImageGeneration(it)
                    //onDarkMode(it)
                    // switchOn = !switchOn
                    //darkMode.value = !darkMode.value
                    //onDarkMode(darkMode.value)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    uncheckedTrackColor = MaterialTheme.colorScheme.onTertiary,
                )


            )
            //SwitchButton(darkMode){ onDarkMode(it) }
        }
        /* Divider( modifier = Modifier
            .padding(start = 63.dp)
            ,
            color = MaterialTheme.colorScheme.tertiary,
        )*/

        if (isSubMode) {

            if (isSubscribed) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            runCatching {
                                onCloseAction()
                                uriHandler.openUri("http://play.google.com/store/account/subscriptions?package=${BuildConfig.APPLICATION_ID}&sku=${Constants.SUBSCRIPTION_PRODUCT_ID}")
                            }.onFailure { it.printStackTrace() }

                        }
                        .padding(top = 18.dp, bottom = 18.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Subscriptions,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(width = 27.dp, height = 27.dp)

                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.manage_sub),
                        /* color = MaterialTheme.colorScheme.surface,*/
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRestore()
                            onCloseAction()
                        }
                        .padding(top = 18.dp, bottom = 18.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restore,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(width = 27.dp, height = 27.dp)

                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.restore_purchase),
                        /* color = MaterialTheme.colorScheme.onSurface,*/
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                }
            }

            /* Divider( modifier = Modifier
                .padding(start = 63.dp),
                color = MaterialTheme.colorScheme.tertiary
            )*/
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    kotlin
                        .runCatching {
                            onCloseAction()
                            ShareCompat
                                .IntentBuilder(context)
                                .setType("text/plain")
                                .setChooserTitle(context.getString(R.string.share_app))
                                .setText("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                                .startChooser()
                        }
                        .onFailure { it.printStackTrace() }
                }
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.share),
                /* color = MaterialTheme.colorScheme.onSurface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        /* Divider( modifier = Modifier
            .padding(start = 63.dp),
            color = MaterialTheme.colorScheme.tertiary
        )*/

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    runCatching {
                        onCloseAction()
                        uriHandler.openUri(Constants.PRIVACY_POLICY)
                    }.onFailure { it.printStackTrace() }
                }
                .padding(top = 18.dp, bottom = 18.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.PrivacyTip,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.privacy_policy),
                /* color = MaterialTheme.colorScheme.onSurface,*/
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            /* Icon(
                 imageVector = Icons.Default.ArrowForwardIos,
                 contentDescription = null,
                 tint = MaterialTheme.colorScheme.surface,
                 modifier = Modifier
                     .size(25.dp)
             )*/
        }

        /*  Divider( modifier = Modifier
            .padding(start = 63.dp),
            color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp,
        )*/
        if (Constants.SignInMode != SignInType.Guest) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //onCloseAction()
                        logoutDialog = true
                        //navigateToLogout()
                    }
                    .padding(top = 18.dp, bottom = 18.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(width = 27.dp, height = 27.dp)

                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = stringResource(id = if (isGuest.not()) R.string.logout else R.string.exit_guest),
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Barlow,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
            }

            if (isGuest.not()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            deleteAccountDialog = true
                        }
                        .padding(top = 20.dp, bottom = 20.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(width = 27.dp, height = 27.dp)

                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.delete_account),
                        color = MaterialTheme.colorScheme.error,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                }
            }

        }
        //}
    }
}