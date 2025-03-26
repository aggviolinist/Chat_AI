package com.apps.imageAI.ui.recents

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.imageAI.R
import com.apps.imageAI.components.Constants
import com.apps.imageAI.components.ConversationType
import com.apps.imageAI.components.Utils
import com.apps.imageAI.components.toFormattedDate
import com.apps.imageAI.data.model.RecentChat
import com.apps.imageAI.ui.MainActivityViewModel
import com.apps.imageAI.ui.ui_components.BannerAdView
import com.apps.imageAI.ui.ui_components.ImageTextButton
import com.apps.imageAI.ui.ui_components.ToolBar
import com.apps.imageAI.ui.ui_components.TopBarSearch
import com.apps.imageAI.ui.dialogs.ConfirmationDialog
import com.apps.imageAI.ui.dialogs.FreeCreditsDialog
import com.apps.imageAI.ui.theme.Labrada
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun RecentChatsScreen(
    navigateToChat: (chatId: Long?, String) -> Unit,
    navigateToSubscription: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: RecentChatsViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel = hiltViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
) {
    val darkMode by mainActivityViewModel.darkMode.collectAsState()
    val isImageGen by mainActivityViewModel.isImageGeneration.collectAsState()
    val recentChats by viewModel.recentChats.collectAsState()
    val isCreditsPurchased by viewModel.isCreditsPurchased.collectAsState()
    val isFreeCredits by viewModel.isFreeCreditsReceived.collectAsState()
    val isSubMode = true
    val isLoading by viewModel.isLoading.collectAsState()
    var isPremiumScreenDisplayed by rememberSaveable {
        mutableStateOf(false)
    }
    var isCreditDialogDisplayed by rememberSaveable {
        mutableStateOf(false)
    }

    var clearConversationDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val currentLanguageCode = viewModel.getCurrentLanguageCode()
        Utils.changeLanguage(context, currentLanguageCode)
        Utils.changeLanguage(context.applicationContext, currentLanguageCode)
        viewModel.loadThemeMode()
        viewModel.getAllChats()
        delay(800)
        if (isSubMode && isPremiumScreenDisplayed.not() && isCreditsPurchased.not()) {
            navigateToSubscription()
            isPremiumScreenDisplayed = true
        }
    }

    val activity = LocalContext.current as Activity
    var isSearchBar by remember { mutableStateOf(false) }
    BackHandler(true) {
        if (isSearchBar.not()) {
            activity.finish()
        } else {
            isSearchBar = false
            viewModel.resetSearch()
        }
    }

    if (isFreeCredits && isCreditDialogDisplayed.not()) {
        isPremiumScreenDisplayed = true
        FreeCreditsDialog(Constants.DAILY_FREE_CREDITS) {
            isCreditDialogDisplayed = true
            viewModel.resetCreditsDialog()
        }
    }

    if (clearConversationDialog) {
        ConfirmationDialog(title = stringResource(R.string.confirmation), message = stringResource(R.string.are_you_sure_delete_all_history), onCancel = {
            clearConversationDialog = false
        }) {
            viewModel.clearAllChats()
            clearConversationDialog = false
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 1.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            if (isSearchBar) {
                TopBarSearch(onSearchText = {
                    viewModel.searchChats(it)
                }) {
                    isSearchBar = !isSearchBar
                    viewModel.resetSearch()
                }
            } else {
                ToolBar(
                    onClickAction = {
                        mainActivityViewModel.resetDrawer()
                        openDrawer()
                    },
                    image = R.drawable.round_menu_24,
                    text = stringResource(R.string.convo),
                    MaterialTheme.colorScheme.onBackground,
                    showDivider = recentChats.isEmpty().not(),
                    optionMenuItems = {
                        if (recentChats.isNotEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        isSearchBar = !isSearchBar
                                    },
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "image",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .width(27.dp)
                                            .height(27.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(15.dp))

                                IconButton(
                                    onClick = {
                                        clearConversationDialog = true
                                    },
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "image",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .width(27.dp)
                                            .height(27.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            }

            if (isCreditsPurchased.not()) {
                BannerAdView()
            }

            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (recentChats.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.55f
                    )
                }
            } else {
                val previousColorState = remember { mutableStateOf<Color?>(null) }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = if (isCreditsPurchased.not()) 8.dp else 12.dp, bottom = 2.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    items(items = recentChats, key = { it.id }) { conversation ->
                        RecentItem(
                            recentItem = conversation,
                            onItemClick = {
                                navigateToChat(
                                    it.id,
                                    it.type
                                )
                            },
                            onDelete = {
                                viewModel.deleteChatById(it.id)
                            },
                            previousColorState = previousColorState
                        )
                    }
                }
            }
        }

        var clickTs by remember {
            mutableStateOf(System.currentTimeMillis())
        }

        val margin = if (isImageGen) 14.dp else 60.dp
        val padding = if (isImageGen) 7.dp else 60.dp
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = if (recentChats.isEmpty()) 50.dp else 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            // Generate Text Button
            var isTextButtonClicked by remember { mutableStateOf(false) }

            val textInfiniteTransition = rememberInfiniteTransition(label = "")
            val textOffsetY by textInfiniteTransition.animateFloat(
                initialValue = -5f,
                targetValue = 5f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            ImageTextButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = margin, end = padding)
                    .offset(x = 0.dp, y = if (!isTextButtonClicked) textOffsetY.dp else 0.dp),
                text = stringResource(id = R.string.generate_text),
                imageId = R.drawable.outline_chat_24,
                isDarkMode = darkMode,
                onClick = {
                    isTextButtonClicked = true
                    val currentTs = System.currentTimeMillis()
                    if (currentTs - clickTs < 1001) {
                        return@ImageTextButton
                    }

                    clickTs = currentTs
                    navigateToChat(
                        null,
                        ConversationType.TEXT.name
                    )
                }
            )

            if (isImageGen) {
                // Generate Image Button
                var isImageButtonClicked by remember { mutableStateOf(false) }

                val imageInfiniteTransition = rememberInfiniteTransition(label = "")
                val imageOffsetY by imageInfiniteTransition.animateFloat(
                    initialValue = -5f,
                    targetValue = 5f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )

                ImageTextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = padding, end = margin)
                        .offset(x = 0.dp, y = if (!isImageButtonClicked) imageOffsetY.dp else 0.dp),
                    text = stringResource(id = R.string.generate_image),
                    imageId = R.drawable.outline_image_24,
                    isDarkMode = darkMode,
                    onClick = {
                        isImageButtonClicked = true
                        navigateToChat(
                            null,
                            ConversationType.IMAGE.name
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RecentItem(
    recentItem: RecentChat,
    onItemClick: (RecentChat) -> Unit,
    onDelete: (RecentChat) -> Unit,
    previousColorState: MutableState<Color?>
) {
    val scope = rememberCoroutineScope()
    val currentItem by rememberUpdatedState(recentItem)
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val colors = remember {
        listOf(
            Color(0xFF8A9A5B),
            Color(0xFFE48400),
            Color(0xFF795548),
            Color(0xFF607D8B),
            Color(0xFF009688),
            Color(0xFF3F51B5),
            Color(0xFF9C27B0),
            Color(0xFF00BCD4),
            Color(0xFF008080),
            Color(0xFFCF9FFF),
            Color(0xFFFFC0CB)
        )
    }

    val randomColor = remember(recentItem.id) {
        var newColor = colors.random()
        while (newColor == previousColorState.value) {
            newColor = colors.random()
        }
        previousColorState.value = newColor
        return@remember newColor
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .indication(interactionSource, LocalIndication.current)
            .fillMaxWidth()
            .background(
                randomColor.copy(alpha = 0.05f), // Very light background
                RoundedCornerShape(12.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        expanded = true
                    }, onTap = {
                        scope.launch {
                            delay(100)
                            onItemClick(currentItem)
                        }
                    }, onPress = { offset ->
                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = currentItem.title.replaceFirstChar { it.uppercase() },
                color = randomColor, // Use the random color for text
                maxLines = 1,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W700,
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentItem.type.contentEquals(ConversationType.IMAGE.name) && currentItem.content.isNotEmpty()) {
                    GlideImage(
                        imageModel = { currentItem.content },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop
                        ),
                        modifier = Modifier
                            .size(25.dp)
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }

                val content =
                    if (currentItem.content.isNotEmpty()) currentItem.content else currentItem.createdAt.toFormattedDate()
                Text(
                    text = if (currentItem.type.contentEquals(ConversationType.IMAGE.name)) "Image" else content,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                    ), color = randomColor, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(23.dp)
        )

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = MaterialTheme.colorScheme.background),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(6.dp))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onSecondary,
                        RoundedCornerShape(6.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onTertiary,
                        RoundedCornerShape(6.dp)
                    ),
                properties = PopupProperties(focusable = false)
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onDelete(currentItem)
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.delete),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            "DeleteConversation",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}