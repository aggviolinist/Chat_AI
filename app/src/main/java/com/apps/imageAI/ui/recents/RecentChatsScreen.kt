package com.apps.imageAI.ui.recents

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.apps.imageAI.ui.ui_components.ToolBar
import com.apps.imageAI.ui.ui_components.TopBarSearch
import com.apps.imageAI.ui.dialogs.ConfirmationDialog
import com.apps.imageAI.ui.dialogs.FreeCreditsDialog
import com.apps.imageAI.ui.theme.Barlow
import com.apps.imageAI.ui.ui_components.BannerAdView
import com.apps.imageAI.ui.ui_components.ImageTextButton
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun RecentChatsScreen(
    navigateToChat: ( chatId:Long?,String) -> Unit,
    navigateToSubscription:()->Unit,
    openDrawer: () -> Unit,
    viewModel: RecentChatsViewModel = hiltViewModel(), mainActivityViewModel: MainActivityViewModel = hiltViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
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
        if (isSubMode && isPremiumScreenDisplayed.not() && isCreditsPurchased.not() )
        {
            navigateToSubscription()
            isPremiumScreenDisplayed= true
        }



    }
    val activity = LocalContext.current as Activity
    var isSearchBar by remember { mutableStateOf(false) }

    BackHandler(true) {

        if (isSearchBar.not())
        {
            activity.finish()
        }else{
            isSearchBar = false
        viewModel.resetSearch()
        }
    }

    if (isFreeCredits && isCreditDialogDisplayed.not()) {
        isPremiumScreenDisplayed= true // ignore subs screen display when credits dialog is displayed
        FreeCreditsDialog(Constants.DAILY_FREE_CREDITS) {
            isCreditDialogDisplayed = true
            viewModel.resetCreditsDialog()
        }
    }

    if (clearConversationDialog)
    {
        ConfirmationDialog(title = stringResource(R.string.confirmation), message = stringResource(R.string.are_you_sure_delete_all_history), onCancel = {
            clearConversationDialog = false
        }) {
            viewModel.clearAllChats()
            clearConversationDialog=false
        }
    }

    Box(
        Modifier
            .fillMaxSize()/*.background(MaterialTheme.colorScheme.background)*/
            .padding(bottom = 1.dp)) {
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
                    onClickAction = { mainActivityViewModel.resetDrawer()
                        openDrawer()},
                    image = R.drawable.round_menu_24,
                    text = stringResource(R.string.conversations),
                    MaterialTheme.colorScheme.onBackground,
                    showDivider = recentChats.isEmpty().not(),
                    optionMenuItems = {
                        if (recentChats.isNotEmpty()){
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
                                    imageVector =Icons.Filled.Search ,
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
                                          clearConversationDialog=true
                                },
                                modifier = Modifier
                                    .width(27.dp)
                                    .height(27.dp)
                            ) {

                                Icon(
                                    imageVector =Icons.Outlined.Delete,
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

            if (isCreditsPurchased.not())
            {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        /*painter = painterResource(id = if (darkMode) R.drawable.outline_empty else R.drawable.outline_empty_light),*/
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.no_chats),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            lineHeight = 25.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 15.dp)

                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = if (isCreditsPurchased.not())8.dp else 12.dp, bottom = 2.dp)
                        .padding(horizontal = 8.dp)
                ) {

                    items(items = recentChats, key = { it.id }) { conversation ->

                        RecentItem(recentItem = conversation, onItemClick = {
                            navigateToChat(
                                it.id,
                                it.type
                            )
                        }){
                            viewModel.deleteChatById(it.id)
                        }

                    }
                }

            }

        }
        var clickTs by remember {
            mutableStateOf(System.currentTimeMillis())
        }

        val margin = if (isImageGen)14.dp else 60.dp
        val padding = if (isImageGen) 7.dp else 60.dp

        Row (
            Modifier
                .fillMaxWidth()
                .padding(bottom = if (recentChats.isEmpty()) 50.dp else 16.dp)
                .align(Alignment.BottomCenter)) {
            ImageTextButton(
                Modifier
                    .weight(1f)
                    .padding(start = margin, end = padding),text = stringResource(id = R.string.generate_text), imageId = R.drawable.outline_chat_24, isDarkMode = darkMode) {
                val currentTs = System.currentTimeMillis()
                if (currentTs-clickTs<1001)
                {
                    return@ImageTextButton
                }
                clickTs = currentTs

                navigateToChat(
                    null,
                    ConversationType.TEXT.name
                )
            }
            if (isImageGen)
            {
                ImageTextButton(
                    Modifier
                        .weight(1f)
                        .padding(start = padding, end = margin/*14.dp*/),
                    text = stringResource(id = R.string.generate_image),
                    imageId = R.drawable.outline_image_24,
                    isDarkMode = darkMode
                ) {
                    navigateToChat(
                        null,
                        ConversationType.IMAGE.name
                    )
                }
            }
        }
    }
}


@Composable
fun RecentItem(recentItem : RecentChat, onItemClick:(RecentChat)->Unit, onDelete:(RecentChat)->Unit)
{
    val scope = rememberCoroutineScope()
    val currentItem by rememberUpdatedState(recentItem)

    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .indication(interactionSource, LocalIndication.current)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onSecondary,
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
                        //tapped = true

                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)

                        tryAwaitRelease()

                        interactionSource.emit(PressInteraction.Release(press))

                        //tapped = false

                    }
                )
            }
           /* .border(
                2.dp,
                MaterialTheme.colorScheme.onTertiary,
                RoundedCornerShape(16.dp)
            )*/
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
               /* .padding(start = 8.dp, top = 4.dp, bottom = 6.dp)*/
        ) {
            Text(
                text = currentItem.title.replaceFirstChar { it.uppercase() },
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    /*fontFamily = Barlow,*/

                    )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row (verticalAlignment = Alignment.CenterVertically){

                if (currentItem.type.contentEquals(ConversationType.IMAGE.name) && currentItem.content.isNotEmpty())
                {
                    GlideImage(
                        imageModel = {currentItem.content},
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

                val content = if (currentItem.content.isNotEmpty()) currentItem.content else currentItem.createdAt.toFormattedDate()

                Text(
                    text = if (currentItem.type.contentEquals(ConversationType.IMAGE.name)) "Image" else content/*createdAt.toFormattedDate()*/,
                   /* color = MaterialTheme.colorScheme.onSurface,*/
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                       /* fontFamily = Barlow,*/

                        ), maxLines = 1, overflow = TextOverflow.Ellipsis
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

            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
    //Divider( color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
}
