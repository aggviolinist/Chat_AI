package com.apps.imageAI.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.apps.imageAI.components.ApiKeyHelpers
import com.apps.imageAI.components.CreditHelpers
import com.apps.imageAI.ui.navigation.NavigationGraph
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.BuildConfig
import com.apps.imageAI.components.AppLogger
import com.apps.imageAI.components.InAppPurchaseHelper
import com.apps.imageAI.components.Utils
import com.apps.imageAI.ui.drawer.AppDrawerContent
import com.apps.imageAI.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

private const val TAG="MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiKeyHelpers: ApiKeyHelpers
    @Inject
    lateinit var creditsKeyHelpers: CreditHelpers
    @Inject
    lateinit var inAppPurchaseHelper: InAppPurchaseHelper

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLogger.logE(TAG,"onCreate")

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition viewModel.isLoading.value
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        inAppPurchaseHelper.billingSetup()
        apiKeyHelpers.connect()
        creditsKeyHelpers.resetFreeCredits()
        creditsKeyHelpers.connect()

        val startDestination = if (FirebaseAuth.getInstance().currentUser != null || viewModel.isGuestMode()) Screen.RecentChats.route else Screen.Welcome.route

        setContent {
            val darkTheme by viewModel.darkMode.collectAsState()
            viewModel.getCurrentLanguageCode()
            val currentLanguageCode by viewModel.currentLanguageCode.collectAsState()
            Utils.changeLanguage(this@MainActivity,currentLanguageCode)
            ImageAITheme(darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.systemBarsPadding().fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            AppDrawerContent(navigateLanguages = {
                                navController.navigate(route = Screen.Language.route)
                            },navigateSubscription = {
                                navController.navigate(route = Screen.Subscription.route)
                            }, onLogout = {
                                if (FirebaseAuth.getInstance().currentUser!=null)
                                {
                                    FirebaseAuth.getInstance().signOut()
                                    viewModel.resetGuestMode()
                                }else{
                                    viewModel.resetGuestMode()
                                }

                                disconnectFireBaseHelpers()
                                navController.navigate(Screen.Welcome.route){
                                    popUpTo(Screen.RecentChats.route) {
                                        inclusive = true
                                    }
                                }
                            }, onCloseAction = {
                                scope.launch {  drawerState.close()}
                            },inAppPurchaseHelper)
                        },
                        gesturesEnabled = false
                    ) {
                        NavigationGraph(navController = navController, startDestination = startDestination,drawerState,inAppPurchaseHelper)
                    }
                }
            }
        }

        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted)
        {
            if (BuildConfig.DEBUG.not())
            {
                showRootedAlert()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        AppLogger.logE(TAG,"onResume")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppLogger.logE(TAG,"onBackPressed")
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppPurchaseHelper.disconnect()
        apiKeyHelpers.disconnect()
        creditsKeyHelpers.disconnect()
    }

    fun disconnectFireBaseHelpers(){
        AppLogger.logE(TAG,"disconnectFireBaseHelpers")
        apiKeyHelpers.disconnect()
        creditsKeyHelpers.disconnect()
    }

    private fun showRootedAlert(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Can't Open App!")
            .setMessage("For security reason image-AI can't be opened on a rooted device")
            .setCancelable(false)
            .setNeutralButton("Ok"){d,w-> finish()}

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}


