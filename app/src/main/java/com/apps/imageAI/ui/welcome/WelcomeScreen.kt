package com.apps.imageAI.ui.welcome

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.imageAI.ui.theme.Montserrat
import com.apps.imageAI.R
import com.apps.imageAI.components.Constants
import com.apps.imageAI.components.CredentialManagerResultContract
import com.apps.imageAI.components.GoogleAuthClient
import com.apps.imageAI.components.GoogleAuthHelper
import com.apps.imageAI.components.SignInType
import com.apps.imageAI.ui.theme.ImageAITheme
import com.apps.imageAI.ui.theme.Labrada
import com.apps.imageAI.ui.theme.stronglyDeemphasizedAlpha
import com.apps.imageAI.ui.ui_components.GoogleLoginButton
import com.apps.imageAI.ui.ui_components.NormalLoginButton
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

private const val TAG ="WelcomeScreen"
@Composable
fun WelcomeScreen(navigateToRecentChat: () -> Unit, viewModel: WelcomeViewModel = hiltViewModel()) {
    var loginError by remember { mutableStateOf(viewModel.authError) }
    val context = LocalContext.current
    val credentialsManager = GoogleAuthClient.getCredentialManager(context)
    val googleAuthHelper = remember { GoogleAuthHelper(context) }

    val authResultLauncher = rememberLauncherForActivityResult(contract = CredentialManagerResultContract()) { credential ->
        if (credential != null) {
            try {
                val googleIdTokenCredential = GoogleAuthClient.parseGoogleIdToken(credential)
                val idToken = googleIdTokenCredential.idToken
                if (idToken != null) {
                    viewModel.authenticateWithToken(idToken)
                } else {
                    loginError = true
                    viewModel.updateProcessingState(false)
                }
            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
                loginError = true
                viewModel.updateProcessingState(false)
            }
        } else {
            loginError = true
            viewModel.updateProcessingState(false)
        }
    }

    val signInResult by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(signInResult)
    {
        if (signInResult)
        {
            Log.e(TAG,"signin trigger with coroutine")
            navigateToRecentChat()
        }

    }

    WelcomeUI(onGoogleButtonClick = {
        if (!viewModel.isProcessing) {
            loginError = false
            viewModel.updateProcessingState(true)
            googleAuthHelper.signIn(
                onSuccess = { googleIdTokenCredential ->
                    viewModel.authenticateWithToken(googleIdTokenCredential.idToken ?: "")
                },
                onGetIntentSender = { intentSenderRequest ->
                    authResultLauncher.launch(intentSenderRequest)
                },
                onError = { exception ->
                    exception.printStackTrace()
                    loginError = true
                    viewModel.updateProcessingState(false)
                }
            )
        }
    }, onNormalButtonClick = {
        if (!viewModel.isProcessing)
        {
            loginError = false
            viewModel.updateProcessingState(true)
            viewModel.loginWithEmailAndPass()
        }
    },viewModel.isProcessing,loginError)
}

@Composable
fun WelcomeUI(onGoogleButtonClick:()->Unit,onNormalButtonClick:()->Unit,isProcessing:Boolean,isLoginError:Boolean)
{
    Box(modifier  = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background) ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp, bottom = 50.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(180.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text(modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(imageVector = Icons.Outlined.Bolt, contentDescription ="powered by icon",colorFilter = ColorFilter.tint(
                    Color.Green
                ) )
                Text(
                    text = stringResource(R.string.powered_by),
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )

            }
            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                if (Constants.SignInMode == SignInType.Both || Constants.SignInMode == SignInType.Gmail) {
                    Spacer(modifier = Modifier.height(25.dp))
                    GoogleLoginButton(
                        text = stringResource(id = R.string.sign_in_with_google),
                        onClick = onGoogleButtonClick
                    )
                }
                if (Constants.SignInMode == SignInType.Both ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.or),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
                    )
                }

                if (Constants.SignInMode == SignInType.Both || Constants.SignInMode == SignInType.Guest) {
                    Spacer(modifier = Modifier.height(20.dp))
                    NormalLoginButton(
                        text = stringResource(if (Constants.SignInMode == SignInType.Guest) R.string.user_continue else R.string.continue_guest),
                        onClick = onNormalButtonClick
                    )
                }

                if (isProcessing)
                {
                    Spacer(modifier = Modifier.height(12.dp))
                    CircularProgressIndicator()
                }
                if (isLoginError)
                {
                    Spacer(modifier = Modifier.height(15.dp))
                    TextFieldError(textError = stringResource(id = R.string.google_signin_error))
                }
            }

        }

        Column(Modifier.align(Alignment.BottomEnd)) {
            HorizontalDivider( modifier = Modifier
                .padding(start = 4.dp)
                .padding(end = 4.dp),
                color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp,
            )
            PolicyText()
        }
    }
}

@Composable
fun PolicyText() {
    val uriHandler = LocalUriHandler.current
    val terms = stringResource(id = R.string.terms_service)
    val privacy = stringResource(id = R.string.privacy_policy)

    val annotatedString = buildAnnotatedString {
        append("${stringResource(id = R.string.policy_text)} ")
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = terms, annotation = terms)
            append(terms)
        }
        append(" & ")
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = privacy, annotation = privacy)
            append(privacy)
        }
    }
    ClickableText(text = annotatedString,style = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    ), modifier = Modifier.padding(start = 55.dp,5.dp), onClick ={ offset->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { span ->
                if (span.item.contentEquals(terms))
                {
                    runCatching {
                        uriHandler.openUri(Constants.TERMS_SERVICE)
                    }.onFailure { it.printStackTrace() }
                }
                else
                {
                    runCatching {
                        uriHandler.openUri(Constants.PRIVACY_POLICY)
                    }.onFailure { it.printStackTrace() }
                }
            }
    } )
}
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "Welcome light theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
/*@Preview(name = "Welcome dark theme", uiMode = Configuration.UI_MODE_NIGHT_NO)*/
@Composable
fun WelcomePreview(){
    ImageAITheme {

        WelcomeUI(
            onGoogleButtonClick = {  }, onNormalButtonClick = {},
            isProcessing = false ,
            isLoginError = true
        )
    }
}