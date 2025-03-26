package com.apps.imageAI.ui.chats

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.apps.imageAI.R
import com.apps.imageAI.components.Constants
import com.apps.imageAI.components.ConversationType
import com.apps.imageAI.components.Utils
import com.apps.imageAI.components.displayIntersAd
import com.apps.imageAI.components.loadAdInters
import com.apps.imageAI.data.model.GPTModel
import com.apps.imageAI.data.model.GenerationModel
import com.apps.imageAI.data.model.ImagePromptType
import com.apps.imageAI.data.model.ImageUri
import com.apps.imageAI.ui.credits_info.NoCreditsInfoBottomSheet
import com.apps.imageAI.ui.prompts.AiPromptsSheet
import com.apps.imageAI.ui.styles.StylesSheet
import com.apps.imageAI.ui.theme.Labrada
import com.apps.imageAI.ui.theme.CreditsTint
import com.apps.imageAI.ui.ui_components.EditTextField
import com.apps.imageAI.ui.ui_components.Examples
import com.apps.imageAI.ui.ui_components.BannerAdView
import com.apps.imageAI.ui.ui_components.ImageExamples
import com.apps.imageAI.ui.ui_components.ImageInputCard
import com.apps.imageAI.ui.ui_components.MessageBubble
import com.apps.imageAI.ui.ui_components.PDFInputCard
import com.apps.imageAI.ui.ui_components.StopGenerateButton
import com.apps.imageAI.ui.ui_components.ToolBarChat
import kotlinx.coroutines.launch


private const  val ANIMATION_DURATION = 50
@Composable
fun ChatBoardScreen(navigateToBack: () -> Unit,
                    navigateToPremium: () -> Unit,
                    data: ChatData,
                    viewModel: ChatBoardViewModel = hiltViewModel(), savedStateHandle: SavedStateHandle? = null)
{


    val messages by viewModel.messages.collectAsState()
    val creditsCount by viewModel.creditsCount.collectAsState()
    val isAiProcessing by viewModel.isAiProcessing.collectAsState()
    val minCreditsRequired by viewModel.minCreditsRequired.collectAsState()
    val conversationType by viewModel.currentConversationType.collectAsState()
    val isPremium by viewModel.isCreditsPurchased.collectAsState()
    val displayMode by viewModel.displayType.collectAsState()
    val examples by viewModel.examples.collectAsState()
    val isSubMode = true
    val title = viewModel.title.value
    val context = LocalContext.current
    val isImageInput by viewModel.isImageSelected
    val imageUri by viewModel.imageUri
    val pdfUri by viewModel.pdfUri
    val selectedStyle by viewModel.selectedStyle.collectAsState()
    val isImageLoadingFailed = remember { mutableStateOf(false) }
    var inputText by remember {
        mutableStateOf("")
    }
    var showNoCreditsBottomSheet by remember { mutableStateOf(false) }
    var showPromptSheet by remember { mutableStateOf(false) }
    var showMediaSourceSheet by remember { mutableStateOf(false) }
    var linkInputDialog by remember { mutableStateOf(false) }
    var showStylesSheet by remember { mutableStateOf(false) }
    var showImageDetailSheet by remember { mutableStateOf(false) }
    var imageUrlForDetail by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.initWithArg(data)
        if (isPremium.not())
        {
            loadAdInters(context)
        }
    }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageURI ->
        if (imageURI != null) {
            viewModel.setInputImage(ImageUri(imageURI))
        }
    }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { pdfUri ->
        if (pdfUri != null) {
            viewModel.setInputPDF(ImageUri(pdfUri))
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            if (it) {
               viewModel.cameraUri?.let {
                viewModel.setInputImage(it)
               }
            }
        }

    if (linkInputDialog)
    {
        URLInputDialog(onContinue = {
            linkInputDialog=false
            viewModel.setInputImage(ImageUri(Uri.parse(it), link = it))
        }) {
            linkInputDialog = false
        }
    }

    BackHandler {
        if (viewModel.showAds.value && isPremium.not())
        {
            displayIntersAd(context)
        }
        viewModel.cancelMessageJob()
        navigateToBack()
    }

    val listBottomPadding = animateDpAsState(
            if (isAiProcessing) {
                100.dp
            } else {
                0.dp
            },
            animationSpec = tween(ANIMATION_DURATION), label = ""
        )

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // toolbar
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ToolBarChat(
                onClickAction = {
                    viewModel.cancelMessageJob()
                    navigateToBack()
                    if (viewModel.showAds.value && isPremium.not())
                    {
                        displayIntersAd(context)
                    }
                },
                onStyleAction = {if (conversationType == ConversationType.IMAGE){showStylesSheet=true} },
                image = R.drawable.round_arrow_back_24,
                text = if (conversationType == ConversationType.TEXT) {
                        stringResource(R.string.generate_text)
                    } else {
                       if (Constants.ImageGenerationPlatform == GenerationModel.STABILITY)
                           stringResource(R.string.default_style,selectedStyle.name)
                       else
                           stringResource(R.string.generate_image)

                    }  ,
                MaterialTheme.colorScheme.onBackground,creditsCount=creditsCount,
                isStyleMode = conversationType == ConversationType.IMAGE && Constants.ImageGenerationPlatform == GenerationModel.STABILITY,
                isPremium = isPremium, isSubMode = isSubMode
            )

        }

        if (isImageInput.not()) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                if (displayMode == DisplayType.EXAMPLE) {
                    if (isPremium.not()) {
                        BannerAdView(Constants.BANNER_AD_UNIT_ID2)
                    }
                    if (conversationType == ConversationType.TEXT) {
                        Examples(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            examples = examples,
                            image = viewModel.examplesImage,
                            inputText = if (title.isEmpty()) stringResource(id = R.string.samples) else title,
                            onInput = { inputText = it }) {
                            showPromptSheet = true
                        }
                    } else {

                        ImageExamples(inputText = stringResource(id = R.string.image_suggestion), onInput ={inputText=it} )
                    }
                } else {


                    val lazyListState = rememberLazyListState()
                    LaunchedEffect(messages.size) {
                        lazyListState.scrollToItem(0)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(bottom = listBottomPadding.value)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(top = 120.dp, bottom = 8.dp),
                        reverseLayout = true,
                        state = lazyListState
                    ) {
                        items(items = messages,
                            key = { message -> message.id }) { message ->
                            MessageBubble(message = message, onImage = {
                                imageUrlForDetail = it
                                showImageDetailSheet = true
                            })
                        }
                    }


                }

                Column(
                    Modifier
                        .fillMaxHeight()
                ) {

                    Spacer(modifier = Modifier.weight(1f))
                    AnimatedVisibility(
                        visible = isAiProcessing,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ),
                        content = {
                            StopGenerateButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                isImageGen = false
                            ) {
                                viewModel.stopAIContentGeneration()
                            }
                        })
                }

                if (pdfUri.uri != Uri.EMPTY) {
                    Column(
                        Modifier
                            .fillMaxHeight().background(Color.Black.copy(alpha = 0.6f))
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        PDFInputCard(pdfUri = pdfUri.uri, onPromptSelected = {
                            inputText = it
                        }) {
                            viewModel.resetPDFInput()
                        }
                    }
                }

            }
        }else{

            ImageInputCard( modifier = Modifier
                .weight(1f),imageUri = imageUri.uri,isPremium,isImageLoadingFailed, onPromptSelected ={
                inputText = it
            } , onCancel = {
                isImageLoadingFailed.value = false
                viewModel.resetImageInput()
            })

        }
        NoCreditsInfoBottomSheet(
                    showSheet = showNoCreditsBottomSheet ,
                    minCreditsRequired = minCreditsRequired ,
                    onDismiss = { showNoCreditsBottomSheet=false },
                    onUpgrade = { navigateToPremium()
                    })

        AiPromptsSheet(showSheet = showPromptSheet, onDismiss = { showPromptSheet=false }, selectedPrompt ={ title, type, list, img->
                showPromptSheet=false
                viewModel.updateAssistantsExamples(title,list,img)
            } )

        MediaSourceBottomSheet(
                showSheet = showMediaSourceSheet,
                onCameraAction = { showMediaSourceSheet=false
                    viewModel.createCameraUri(context)
                    scope.launch {  cameraLauncher.launch(viewModel.cameraUri!!.uri) }},
                onGalleryAction = { showMediaSourceSheet=false
                    scope.launch {
                        pickerLauncher.launch("image/*")
                    }
                                  },
                onLink = { showMediaSourceSheet=false
                    linkInputDialog=true
                } ) { showMediaSourceSheet = false }


        StylesSheet(selectedId = selectedStyle.id, showSheet = showStylesSheet, onDismiss = { showStylesSheet=false }, onSelected ={
            showStylesSheet = false
            viewModel.selectStyleWithId(it.id)
        } )

        ImageDetailSheet(mediaPath = imageUrlForDetail, showSheet = showImageDetailSheet, onDismiss = {
            showImageDetailSheet = false
            imageUrlForDetail=""
           if (isPremium.not())
           {
               displayIntersAd(context)
           }
        })
        
        if (isSubMode && isPremium.not() && pdfUri.uri == Uri.EMPTY) {
            Text(
                text = if (conversationType == ConversationType.TEXT) stringResource(
                    id = R.string.usage_message,Constants.CHAT_MESSAGE_COST,Constants.MESSAGES_WORDS_TURBO
                ) else stringResource(
                    id = R.string.usage_image,Constants.BASE_IMAGE_GEN_COST
                ),
                color = CreditsTint,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = Labrada,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 1.dp)
            )
        }
        EditTextField(inputText = inputText,conversationType=conversationType.name, onTextChange = {
            inputText = it
        }, onSendClick = {
                userText->
            if (userText.isNotEmpty()) {

                if (Utils.isConnectedToNetwork(context).not()) {
                    Toast.makeText(context,context.getString(R.string.no_connection_try_again),
                        Toast.LENGTH_LONG).show()
                    return@EditTextField
                }

                if (isImageLoadingFailed.value)
                {
                    Toast.makeText(context,context.getString(R.string.image_load_failed),Toast.LENGTH_LONG).show()
                    return@EditTextField
                }

                if (isImageInput && Constants.IS_IMAGE_INPUT_PAID && isPremium.not())
                {
                    navigateToPremium()
                    return@EditTextField
                }

                if (isAiProcessing.not()) {
                    if (isPremium)
                    {
                        if (isImageInput && viewModel.isVisionDailyLimitReach())
                        {
                            Toast.makeText(context,context.getString(R.string.ImageAI_limit),Toast.LENGTH_LONG).show()
                            return@EditTextField
                        }

                        if (conversationType==ConversationType.IMAGE && viewModel.isGenerationDailyLimitReach())
                        {
                            Toast.makeText(context,context.getString(R.string.gen_limit),Toast.LENGTH_LONG).show()
                            return@EditTextField
                        }

                        if (conversationType==ConversationType.TEXT && isImageInput.not() && viewModel.getGPTModel() == GPTModel.GPT_4 && viewModel.isGpt4DailyLimitReach())
                        {
                            Toast.makeText(context,context.getString(R.string.gpt4_limit),Toast.LENGTH_LONG).show()
                            return@EditTextField
                        }
                    }
                    if (conversationType==ConversationType.TEXT)
                    {
                        viewModel.calculateMinRequiredCredits(userText)
                    }
                    if (userText.isNotBlank()) {

                        if (creditsCount < minCreditsRequired) {
                            val minCred = minCreditsRequired
                            showNoCreditsBottomSheet = true

                            return@EditTextField
                        }

                        if (isImageInput)
                        {
                            val promptType = when(userText){
                                context.getString(R.string.picha_input_p1)-> ImagePromptType.Caption
                                context.getString(R.string.picha_input_p2)-> ImagePromptType.Describe
                                context.getString(R.string.picha_input_p3)-> ImagePromptType.Tags
                                context.getString(R.string.picha_input_p4)-> ImagePromptType.Objects
                                else -> ImagePromptType.Custom
                            }
                            viewModel.sendImagePrompt(userText,promptType)
                        }else if (pdfUri.uri != Uri.EMPTY)
                        {
                            viewModel.sendPDFPrompt(userText)
                        }
                        else{
                            viewModel.sendMessage(userText)
                        }

                        inputText = ""
                    }


                }

            }
        }, onCameraClick = {showMediaSourceSheet = true}, onPDFClick = {scope.launch { pdfPickerLauncher.launch("application/pdf") }})
    }
}
