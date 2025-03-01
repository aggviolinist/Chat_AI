package com.apps.imageAI.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Modern authentication client using Credential Manager and Google Identity Services
 * instead of deprecated GoogleSignInClient.
 */
object GoogleAuthClient {
    /**
     * Creates a request for Google authentication
     *
     * @param context Application context
     * @return GetCredentialRequest configured for Google authentication
     */
    fun createGoogleAuthRequest(context: Context): GetCredentialRequest {
        // Configure Google ID token request option
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(Constants.WEB_CLIENT_ID)
            .setFilterByAuthorizedAccounts(false) // Set to true to only show accounts that have already granted the requested scopes
            .setNonce(null) // Optional nonce for additional security
            .build()

        // Create the credential request with the Google option
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    /**
     * Gets a CredentialManager instance
     *
     * @param context Application context
     * @return CredentialManager instance
     */
    fun getCredentialManager(context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    /**
     * Parse the Google ID token from a credential
     *
     * @param credential The credential received from CredentialManager
     * @return GoogleIdTokenCredential containing user information
     * @throws GoogleIdTokenParsingException if parsing fails
     */
    @Throws(GoogleIdTokenParsingException::class)
    fun parseGoogleIdToken(credential: Credential): GoogleIdTokenCredential {
        // Extract the Google ID token from the credential
        return GoogleIdTokenCredential.createFrom(credential.data)
    }
}

/**
 * A contract to handle the activity result for Credential Manager authentication flow
 */
class CredentialManagerResultContract :
    ActivityResultContract<IntentSenderRequest, Credential?>() {

    override fun parseResult(resultCode: Int, intent: Intent?): Credential? {
        return when (resultCode) {
            Activity.RESULT_OK -> {
                intent?.getParcelableExtra("androidx.credentials.CREDENTIAL")
            }
            else -> null
        }
    }

    override fun createIntent(context: Context, input: IntentSenderRequest): Intent {
        // Create an intent that will launch the intentSender
        return Intent().apply {
            putExtra("androidx.credentials.EXTRA_SENDER", input.intentSender)
        }
    }
}

/**
 * Helper class to handle authentication flow
 */
class GoogleAuthHelper(private val context: Context) {
    private val credentialManager = GoogleAuthClient.getCredentialManager(context)

    /**
     * Starts the authentication flow, handles both silent sign-in and UI-based sign-in
     *
     * @param onSuccess Callback with the parsed Google ID token credential on success
     * @param onGetIntentSender Callback with the intent sender when user interaction is required
     * @param onError Callback with exception on failure
     */
    fun signIn(
        onSuccess: (GoogleIdTokenCredential) -> Unit,
        onGetIntentSender: (IntentSenderRequest) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val request = GoogleAuthClient.createGoogleAuthRequest(context)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Try to get credentials
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                // The result contains a credential that we can parse
                val googleIdTokenCredential = GoogleAuthClient.parseGoogleIdToken(result.credential)

                withContext(Dispatchers.Main) {
                    onSuccess(googleIdTokenCredential)
                }
            } catch (e: GetCredentialException) {
                // Check if user interaction is required
                AppLogger.logE(tag = "GoogleAuthHelper", msg = "Couldn't retrieve user's credentials: ${e.localizedMessage}")
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }
}