package com.apps.aivision.ui.chats

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivision.BuildConfig
import com.apps.aivision.R
import com.apps.aivision.components.ApiKeyHelpers
import com.apps.aivision.components.AppLogger
import com.apps.aivision.components.Constants
import com.apps.aivision.components.Constants.BASE_IMAGE_GEN_COST
import com.apps.aivision.components.Constants.VisionPlatform
import com.apps.aivision.components.ConversationType
import com.apps.aivision.components.CreditHelpers
import com.apps.aivision.components.DownloadStatusEnum
import com.apps.aivision.components.Utils
import com.apps.aivision.components.createImageFile
import com.apps.aivision.components.decodeSampledBitmap
import com.apps.aivision.components.getFileName
import com.apps.aivision.components.toBase64
import com.apps.aivision.data.model.AsticaVisionRequest
import com.apps.aivision.data.model.ChatMessage
import com.apps.aivision.data.model.GPTMessage
import com.apps.aivision.data.model.GPTModel
import com.apps.aivision.data.model.GPTRequestParam
import com.apps.aivision.data.model.GPTRole
import com.apps.aivision.data.model.GenerationModel
import com.apps.aivision.data.model.ImageGenerationStatus
import com.apps.aivision.data.model.ImagePromptType
import com.apps.aivision.data.model.ImageRequest
import com.apps.aivision.data.model.ImageUri
import com.apps.aivision.data.model.PromptModel
import com.apps.aivision.data.model.RecentChat
import com.apps.aivision.data.model.StabilityImageRequest
import com.apps.aivision.data.model.StyleModel
import com.apps.aivision.data.model.VisionContent
import com.apps.aivision.data.model.VisionGenerationType
import com.apps.aivision.data.model.VisionMessage
import com.apps.aivision.data.model.VisionRequest
import com.apps.aivision.data.model.VisionUrlModel
import com.apps.aivision.data.repository.ChatRepository
import com.apps.aivision.data.repository.FirebaseRepository
import com.apps.aivision.data.repository.ImageRepository
import com.apps.aivision.data.repository.LocalResourceRepository
import com.apps.aivision.data.repository.MessageRepository
import com.apps.aivision.data.repository.PreferenceRepository
import com.apps.aivision.data.repository.RecentChatRepository
import com.bumptech.glide.Glide
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.Objects
import javax.inject.Inject

private const val DEFAULT_AI_CONTENT =
    "You are an AI bot created by AskAI."
private const val TAG = "ChatBoardViewModel"
@HiltViewModel
class ChatBoardViewModel @Inject constructor(@ApplicationContext val application: Context,
                                             private val messageRepository: MessageRepository,
                                             private val recentChatRepository: RecentChatRepository,
                                             private val localResourceRepository: LocalResourceRepository,
                                             private val chatRepository: ChatRepository,
                                             private val creditHelpers: CreditHelpers,
                                             private val firebaseRepository: FirebaseRepository,
                                             private val preferenceRepository: PreferenceRepository,
                                             private val apiKeyHelpers: ApiKeyHelpers,
                                             private val imageRepository: ImageRepository
):ViewModel() {

    private var messageJob: Job? = null
    private val apiScope = CoroutineScope(Dispatchers.IO)
    private var apiJob: Job? = null
    private val _messages: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(mutableListOf())
    val messages = _messages.asStateFlow()
    private val _examples: MutableStateFlow<List<String>> = MutableStateFlow(mutableListOf())
    val examples = _examples.asStateFlow()
    var examplesImage:Int? = null
    private val _displayType: MutableStateFlow<DisplayType> = MutableStateFlow(DisplayType.EXAMPLE)
    val displayType get() = _displayType.asStateFlow()
    private val _currentConversationType: MutableStateFlow<ConversationType> = MutableStateFlow(ConversationType.TEXT)
    val currentConversationType get() = _currentConversationType.asStateFlow()
    private val _isAiProcessing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAiProcessing: StateFlow<Boolean> = _isAiProcessing.asStateFlow()
    val title = mutableStateOf("")
    private val _minCreditsRequired = MutableStateFlow(1)
    val minCreditsRequired get() = _minCreditsRequired.asStateFlow()
    val isCreditsPurchased get() = creditHelpers.isCreditsPurchased

    private val isSubscriptionMode = true
    val showAds = mutableStateOf(false)
    val creditsCount get() = creditHelpers.credits

    private var recentConversationId:Long = 0
    private var recentMessageId:Long = 0
    private var content =""
    private var prompt =""

    val imageUri = mutableStateOf(ImageUri(Uri.EMPTY))
    val isImageSelected = mutableStateOf(false)
    var cameraUri:ImageUri?=null
    val pdfUri = mutableStateOf(ImageUri(Uri.EMPTY))


    private var styles = listOf<StyleModel>()
    private val _selectedStyle = MutableStateFlow(StyleModel(application.getString(R.string.style_no),"none", R.drawable.baseline_do_disturb_alt_24))
    val selectedStyle get() = _selectedStyle

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun initWithArg(data: ChatData)
    {
        data.chatId?.let {
            recentConversationId = it
        }
        data.title?.let {
            title.value = it
        }
        _currentConversationType.value = ConversationType.valueOf(data.conversationType)

        if (data.examples.isEmpty())
        {
            _examples.value =   /*if (_currentConversationType.value == ConversationType.TEXT) */ localResourceRepository.getTextExamples()
        }else{
            _examples.value = data.examples
        }


        if (recentConversationId>0)
        {
            loadMessages(recentConversationId)
        }
        viewModelScope.launch {
            if (isSubscriptionMode && isCreditsPurchased.value)
            {
                _minCreditsRequired.value = -10000 //
            } else {
               // _minCreditsRequired.value = getMinRequiredCredits("Text") // init with dummy
                if (_currentConversationType.value == ConversationType.TEXT) {
                    _minCreditsRequired.value = getMinRequiredCredits("Text") // init with dummy
                } else {
                    _minCreditsRequired.value = BASE_IMAGE_GEN_COST
                }
            }

            if (_currentConversationType.value==ConversationType.IMAGE)
            {
                styles = localResourceRepository.getStyles()
                _selectedStyle.value = styles[0]
            }
        }
    }

    private fun loadMessages(chatId:Long)
    {
        messageJob = CoroutineScope(Dispatchers.IO).launch {
            val messageStream = messageRepository.getMessages(chatId)
            messageStream.collect{

                _displayType.value = if(it.isEmpty()) DisplayType.EXAMPLE else DisplayType.MESSAGE
                _messages.value = it

            }
        }
    }

    fun cancelMessageJob(){
        messageJob?.cancel()
    }


    private fun decreaseTextChatCredits(wordsStr:String){
        viewModelScope.launch {
            firebaseRepository.decrementCredits(getCreditsCostForMessage(wordsStr))
            /*if (firebaseRepository.isLoggedIn().not())
            {
                preferenceRepository.updateCredits(creditsCount.value-getCreditsCostForMessage(wordsStr))
            }
            else{
                firebaseRepository.decrementCredits(getCreditsCostForMessage(wordsStr))
            }*/
        }
    }

    private fun decreaseImageCredits() {
        viewModelScope.launch {
            firebaseRepository.decrementCredits(minCreditsRequired.value)
           /* if (firebaseRepository.isLoggedIn().not())
            {
                preferenceRepository.updateCredits(creditsCount.value- minCreditsRequired.value)
            }
            else{
                firebaseRepository.decrementCredits(minCreditsRequired.value)
            }*/
        }
    }

    fun sendMessage(text:String)
    {
        viewModelScope.launch(Dispatchers.Default) {

            if (recentConversationId < 1)
            {
                recentConversationId = recentChatRepository.addChat(RecentChat(title = text, type = _currentConversationType.value.name))
                loadMessages(recentConversationId)
            }
            messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.USER.value, content = text, type = _currentConversationType.value.name))

            prompt = text
            recentMessageId =0
            //runChatAIApi(text)
            if (_currentConversationType.value == ConversationType.TEXT)
            {
                runChatAIApi(text)
            }else{
                runImageGenerateApi(text)
            }
            showAds.value = true
        }
    }

    private fun runChatAIApi(prompt:String){

        val history = messageRepository.getMessages(recentConversationId,2)
        val reqMessages: MutableList<GPTMessage> = mutableListOf(
            GPTMessage(
                role = GPTRole.SYSTEM.value,
                content = DEFAULT_AI_CONTENT
            )
        )
        if (history.isNotEmpty()){
            history.reversed().forEach { obj->
                reqMessages.add(GPTMessage(obj.content, GPTRole.entries.first { it.value == obj.role }.value))
            }
        }

        val flow: Flow<String> = chatRepository.textCompletionsWithStream(
            scope = apiScope,
            GPTRequestParam(
                messages = reqMessages.toList(),
                model = if (isCreditsPurchased.value.not() || getGPTModel()== GPTModel.GPT_3_5_TURBO) GPTModel.GPT_3_5_TURBO.modelName else GPTModel.GPT_4_TURBO.modelName
            )
        )
        content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true
            flow.collect{
                content+=it
                if (recentMessageId>0)
                {
                    messageRepository.updateContent(recentMessageId,content,"")
                }else{
                   recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = content, type = ConversationType.TEXT.name))
                }
            }
            if (!content.contains("Failure!",true))
            {
                if (isSubscriptionMode && isCreditsPurchased.value)
                {
                    Log.e(TAG,"Ignore pro")
                    if (getGPTModel()== GPTModel.GPT_4)
                    {
                        incrementGPT4Count()
                    }
                }else
                {
                    decreaseTextChatCredits("$prompt $content")
                }
            }
            _isAiProcessing.value = false
            recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = if (content.length<100) content else content.substring(0..99)))
        }
    }

    private fun runImageGenerateApi(message: String)
    {
        val imageFlow = generateImageFromText(message,Constants.ImageGenerationPlatform,if (selectedStyle.value.id.contentEquals("none")) null else selectedStyle.value.id )

        content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true

            imageFlow.collect{
                when(it){
                    is ImageGenerationStatus.Generated->{
                        _isAiProcessing.value = false
                        if (isSubscriptionMode && isCreditsPurchased.value)
                        {
                            Log.e(TAG,"Ignore pro")
                            incrementGenerationCount()
                        }
                        else
                        { decreaseImageCredits()
                        }
                        if (recentMessageId<=0)
                        {
                            recentMessageId= messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, content = content, type = ConversationType.IMAGE.name, url = it.path))
                        }else{
                            messageRepository.updateContent(recentMessageId,content,it.path)
                        }
                        messageRepository.updateStatus(recentMessageId, DownloadStatusEnum.DOWNLOADING.value)
                        recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = it.path))

                    }
                    is ImageGenerationStatus.GenerationError->{
                        _isAiProcessing.value = false
                        if (recentMessageId<=0)
                        {
                            recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, content = "Failure! Try again.", type = ConversationType.IMAGE.name, url = ""))
                        }else{
                            messageRepository.updateContent(recentMessageId,"Failure! Try again.","")
                        }
                        recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = ""))
                    }
                    is ImageGenerationStatus.Downloading->{
                        messageRepository.updateStatus(recentMessageId,DownloadStatusEnum.DOWNLOADING.value)
                    }
                    is ImageGenerationStatus.Completed->{
                        messageRepository.updateStatus(recentMessageId,DownloadStatusEnum.COMPLETED.value)
                    }
                    is ImageGenerationStatus.DownloadError->{
                        messageRepository.updateStatus(recentMessageId,DownloadStatusEnum.FAILED.value)
                    }
                }
            }
        }

    }

    fun stopAIContentGeneration()
    {
        viewModelScope.launch {
        apiJob?.cancel()
        _isAiProcessing.value = false
        if (isSubscriptionMode && isCreditsPurchased.value)
        {
            if (getGPTModel()== GPTModel.GPT_4)
            {
                incrementGPT4Count()
            }
            Log.e(TAG,"Ignore pro")
        }else
        {
            decreaseTextChatCredits("$prompt $content")
        }
    }

    }
    fun calculateMinRequiredCredits(input:String)
    {
        if (isSubscriptionMode && isCreditsPurchased.value)
            return

        _minCreditsRequired.value = getMinRequiredCredits(input)
    }


    fun getGPTModel() = if (preferenceRepository.getGPTModel().contentEquals(GPTModel.GPT_4.modelName)) GPTModel.GPT_4 else GPTModel.GPT_3_5_TURBO

    private fun getMinRequiredCredits(input:String):Int {
        val words = input.split("\\s+".toRegex())
        val count = words.count()
        var credits = 1

        when (if (preferenceRepository.getGPTModel().contentEquals(GPTModel.GPT_4.name)) GPTModel.GPT_4 else GPTModel.GPT_3_5_TURBO){
            GPTModel.GPT_4 -> {
                credits = (count * 2) / Constants.MESSAGES_WORDS_GPT4
                if (((count * 2) % Constants.MESSAGES_WORDS_GPT4) > 0) {
                    credits += 1
                }
                credits *= Constants.CHAT_MESSAGE_GPT4_COST

            }
            GPTModel.GPT_3_5_TURBO -> {
                credits = (count * 2) / Constants.MESSAGES_WORDS_TURBO
                if (((count * 2) % Constants.MESSAGES_WORDS_TURBO) > 0) {
                    credits += 1
                }
                credits *= Constants.CHAT_MESSAGE_COST

            }
            else -> {}
        }
        Log.e("Credits","Min Req:${credits} count:${count}")

        return credits
    }

    private fun getCreditsCostForMessage(input:String):Int {
        val words = input.split("\\s+".toRegex())
        val count = words.count()
        var credits = 1

        when (if (preferenceRepository.getGPTModel().contentEquals(GPTModel.GPT_4.name)) GPTModel.GPT_4 else GPTModel.GPT_3_5_TURBO){
            GPTModel.GPT_4 -> {
                credits = count / Constants.MESSAGES_WORDS_GPT4
                if ((count % Constants.MESSAGES_WORDS_GPT4) > 0) {
                    credits += 1
                }
                credits *= Constants.CHAT_MESSAGE_GPT4_COST

            }
            GPTModel.GPT_3_5_TURBO -> {
                credits = count / Constants.MESSAGES_WORDS_TURBO
                if ((count % Constants.MESSAGES_WORDS_TURBO) > 0) {
                    credits += 1
                }
                credits *= Constants.CHAT_MESSAGE_COST

            }
            else -> {}
        }
        //Log.e("Credits ACT","Min Req:${credits} count:${count}")

        return credits
    }

    fun updateAssistantsExamples(nTitle:String,examples:List<String>,image:Int)
    {

        title.value = nTitle
        _examples.value = examples
        examplesImage = image
    }

    fun setInputImage(imgUri:ImageUri)
    {
        imageUri.value = imgUri
        isImageSelected.value = true
    }
    fun resetImageInput(){
        isImageSelected.value= false
        imageUri.value= ImageUri(Uri.EMPTY)
    }
    fun createCameraUri(context: Context){
        val file = context.createImageFile()
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )
        cameraUri = ImageUri(uri = uri,file.absolutePath)
    }

    fun sendImagePrompt(question:String,type: ImagePromptType)= viewModelScope.launch(Dispatchers.IO)
    {

        val uri = imageUri.value
        resetImageInput()
        if (recentConversationId < 1)
        {
            recentConversationId = recentChatRepository.addChat(RecentChat(title = question, type = _currentConversationType.value.name))
            loadMessages(recentConversationId)
        }
        prompt = question
        recentMessageId =0

        var url =""
        url = if (uri.path!=null) {
            uri.path
        }
        else{
            val file = Glide.with(application).asBitmap().load(uri.uri).submit().get()
            Utils.saveBitmapToExternalDir(bitmap = file,application)
        }
        messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.USER.value, content = question, type = _currentConversationType.value.name, url = url))

        val flow: Flow<String> = generateTextFromImage(
            url,prompt,type, generationType = VisionPlatform/*,uri.link!=null*/
        )
        content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true
            flow.collect{
                content+=it
                if (recentMessageId>0)
                {
                    messageRepository.updateContent(recentMessageId,content,"")
                }else{
                    recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = content, type = ConversationType.TEXT.name))
                }
            }
            if (!content.contains("Failure!",true))
            {
                if (isSubscriptionMode && isCreditsPurchased.value)
                {
                    Log.e(TAG,"Ignore pro")
                   incrementVisionCount()
                }else
                {
                    decreaseTextChatCredits("$prompt $content")
                }
            }
            _isAiProcessing.value = false
            recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = if (content.length<100) content else content.substring(0..99)))
        }

        showAds.value = true
    }

    fun sendPDFPrompt(question: String)=viewModelScope.launch(Dispatchers.IO) {
        val uri = pdfUri.value
        resetPDFInput()
        if (recentConversationId < 1)
        {
            recentConversationId = recentChatRepository.addChat(RecentChat(title = question, type = _currentConversationType.value.name))
            loadMessages(recentConversationId)
        }


        val reqMessages: MutableList<GPTMessage> = mutableListOf(
            GPTMessage(
                role = GPTRole.SYSTEM.value,
                content = DEFAULT_AI_CONTENT
            )
        )

        val pdfReader = PdfReader(application.contentResolver.openInputStream(uri.uri))
        val document = PdfDocument(pdfReader)
        val n = document.numberOfPages
        val url =  "${uri.uri.getFileName(application.contentResolver)}::${n}::PDF"
        messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.USER.value, content = question, type = _currentConversationType.value.name, url = url))

        if (n> Constants.MAX_PDF_PAGES_PER_FILE)
        {
            val errorMsg = application.getString(R.string.pdf_max_pages,Constants.MAX_PDF_PAGES_PER_FILE.toString())
            recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = errorMsg, type = ConversationType.TEXT.name))
            recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = errorMsg))
            return@launch
        }

        var extractedText = ""
        val strategy = SimpleTextExtractionStrategy()
        for (i in 0 until n) {
            extractedText += PdfTextExtractor.getTextFromPage(document.getPage( i + 1),strategy).trim()
            if (i<(n-1))
            {
                extractedText+"\n"
            }
        }
        //AppLogger.logE("PDF","content: ${extractedText}")
        if (extractedText.trim().length<2)
        {
            val errorMsg = "Failure!:Could not read this file"
            recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = errorMsg, type = ConversationType.TEXT.name))
            recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = prompt,content = errorMsg))
            return@launch
        }

        var merged =""
        merged = if (question.contentEquals(application.getString(R.string.pdf_input_p1))) {
            "${application.getString(R.string.pdf_question)} \"${extractedText}\""
        }else{
            "${application.getString(R.string.pdf_question_custom,question)} \"${extractedText}\""
        }

        recentMessageId =0
        reqMessages.add(GPTMessage(merged))

        val flow: Flow<String> = chatRepository.textCompletionsWithStream(
            scope = apiScope,
            GPTRequestParam(
                messages = reqMessages.toList(),
                model = GPTModel.GPT_3_5_TURBO.modelName
            )
        )
        var content = ""
        apiJob = apiScope.launch(coroutineExceptionHandler) {
            _isAiProcessing.value = true
            flow.collect{
                content+=it
                if (recentMessageId>0)
                {
                    messageRepository.updateContent(recentMessageId,content,"")
                }else{
                    recentMessageId=messageRepository.addMessage(ChatMessage(recentChatId = recentConversationId, role = GPTRole.ASSISTANT.value, content = content, type = ConversationType.TEXT.name))
                }
            }
            if (!content.contains("Failure!",true))
            {
                if (isSubscriptionMode && isCreditsPurchased.value)
                {
                    Log.e(TAG,"Ignore pro")
                    if (getGPTModel()== GPTModel.GPT_4)
                    {
                        incrementGPT4Count()
                    }
                }else
                {
                    decreaseTextChatCredits("$merged $content")
                }
            }
            _isAiProcessing.value = false
            recentChatRepository.updateChat(RecentChat(id = recentConversationId, title = question,content = if (content.length<100) content else content.substring(0..99)))
        }
        showAds.value = true

      }

    private fun generateTextFromImage (imagePath: String, prompt:String, type: ImagePromptType, generationType: VisionGenerationType, isLink:Boolean=false):Flow<String> {

        val base64:String? = if (isLink.not()) {
            val file = File(imagePath)
            var bitmap = file.decodeSampledBitmap(512, 512)
            //AppLogger.logE(TAG,"bitmap: ${bitmap.width}x${bitmap.height}")
            if (generationType == VisionGenerationType.OPENAI)
            {
                bitmap = Utils.resizeBitmap(bitmap,512,512)

            }
            bitmap.toBase64()
        }else{
            imagePath
        }
        val isCreditPurchased = creditHelpers.isCreditsPurchased.value
        if (generationType == VisionGenerationType.OPENAI) {
            val messages = mutableListOf<VisionMessage>()
            val contentList = mutableListOf<VisionContent>()
            contentList.add(VisionContent(type = "text", text = prompt))
            contentList.add(
                VisionContent(
                    type = "image_url",
                    imageUrl = VisionUrlModel(if(isLink.not()) "data:image/jpeg;base64,${base64}" else base64!!)
                )
            )
            messages.add(VisionMessage(GPTRole.USER.value, contentList))
            val maxToken = if (isCreditPurchased) 150 else 35

            val visionRequest = VisionRequest(model = GPTModel.GPT_4_VISION.modelName, messages,maxToken)

            return chatRepository.textCompletionsWithVision(visionRequest)
        }else{
            var reqPrompt:String? = null
            val params:String = when(type){
                ImagePromptType.Caption -> "describe"
                ImagePromptType.Describe -> {
                    if (isCreditPurchased)
                    {
                        reqPrompt = ""
                        "gpt"
                    }else {
                        "describe"
                    }
                }
                ImagePromptType.Tags -> "tags"
                ImagePromptType.Objects -> "text_read" //text_read,objects
                ImagePromptType.Custom -> {
                    if (isCreditPurchased)
                    {  reqPrompt = prompt
                        "gpt"
                    }else {
                        "describe"
                    }
                }
            }

            val model = if (isCreditPurchased) "2.1_full" else "2.0_full"
            val request = AsticaVisionRequest(key=apiKeyHelpers.getVisionKey(), modelVersion = model, input = base64!!, visionParams = params,prompt = reqPrompt)
            return chatRepository.textCompletionsWithVision(request)
        }

    }

    private fun generateImageFromText( prompt: String,generationModel: GenerationModel,style:String?=null):Flow<ImageGenerationStatus> {

        return if (generationModel==GenerationModel.STABILITY){
            val list = mutableListOf<PromptModel>()
            list.add(PromptModel(prompt,1))
            list.add(PromptModel("blurry, bad",-1))
            imageRepository.generateImageWithStability(StabilityImageRequest(prompts = list, stylePreset = style))
        }else {
            imageRepository.generateImageWithDalle(ImageRequest(prompt,size="1024x1024"))
        }
    }

    fun selectStyleWithId(styleId:String)
    {
        val style = styles.filter { it.id.contentEquals(styleId) }
        if (style.isNotEmpty())
            _selectedStyle.value = style[0]
    }

    fun setInputPDF(imgUri:ImageUri)
    {
        pdfUri.value = imgUri
    }
    fun resetPDFInput(){

        pdfUri.value= ImageUri(Uri.EMPTY)
    }
    fun isVisionDailyLimitReach():Boolean = preferenceRepository.getVisionDailyCount() >= Constants.MAX_VISION_LIMIT_PER_DAY
    fun isGenerationDailyLimitReach():Boolean = preferenceRepository.getGenerationDailyCount() >= Constants.MAX_IMAGE_GEN_LIMIT_PER_DAY
    fun isGpt4DailyLimitReach():Boolean = preferenceRepository.getGPT4DailyCount() >= Constants.MAX_GPT4_MESSAGE_LIMIT_PER_DAY

    private fun incrementVisionCount(){
        preferenceRepository.setVisionDailyCount(preferenceRepository.getVisionDailyCount()+1)
        logsLimits()
    }
    private fun incrementGenerationCount(){
        preferenceRepository.setGenerationDailyCount(preferenceRepository.getGenerationDailyCount()+1)
        logsLimits()
    }
    private fun incrementGPT4Count(){
        preferenceRepository.setGPT4DailyCount(preferenceRepository.getGPT4DailyCount()+1)
        logsLimits()
    }
    private fun logsLimits(){
        AppLogger.logE(TAG," Vision:${preferenceRepository.getVisionDailyCount()} ImageG:${preferenceRepository.getGenerationDailyCount()} gpt4:${preferenceRepository.getGPT4DailyCount()}")
    }

    override fun onCleared() {
        super.onCleared()
        messageJob?.cancel()
    }

}




enum class DisplayType {
    EXAMPLE,MESSAGE
}

data class ChatData(val chatId:Long?=null,val title:String?=null,val conversationType: String = ConversationType.TEXT.name, val examples:List<String> = mutableListOf())