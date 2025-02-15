package com.apps.aivision.ui.ui_components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivision.R
import com.apps.aivision.components.AppLogger
import com.apps.aivision.components.bounceClick
import com.apps.aivision.components.click
import com.apps.aivision.components.getFileName
import com.apps.aivision.components.getPathFileName
import com.apps.aivision.ui.theme.AIVisionTheme
import com.apps.aivision.ui.theme.Barlow
import com.apps.aivision.ui.theme.CreditsTint
import com.apps.aivision.ui.theme.OnSurfaceDark
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

private const val TAG ="PDFInputCard"
@Composable
fun PDFInputCard(modifier: Modifier = Modifier,pdfUri: Uri,onPromptSelected:(String)->Unit,onCancel:()->Unit) {
    
    var fileName by remember {
        mutableStateOf("filename")
    }

    var pageSize by remember {
        mutableStateOf("1")
    }

    val context = LocalContext.current

    LaunchedEffect(Unit ){
        fileName = pdfUri.getFileName(context.contentResolver)
        val pdfReader = PdfReader(context.contentResolver.openInputStream(pdfUri))
        val document = PdfDocument(pdfReader)
        pageSize = document.numberOfPages.toString()

    }

    Box(modifier = modifier/*.background(Color.Black.copy(0.1f))*/) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )

            ) {

                Row {
                    Image(painter = painterResource(R.drawable.ic_pdf), contentDescription = null,
                        Modifier
                            .size(100.dp)
                            .padding(10.dp))
                    Column(modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)) {
                        Text(
                            text = fileName,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily = Barlow
                            ),
                            overflow = TextOverflow.Ellipsis
                            ,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxWidth()
                                .padding(end = 16.dp),


                        )
                        Text(
                            text = stringResource(id = R.string.page_count,pageSize),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily = Barlow
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxWidth()
                                .padding(end = 16.dp),


                            )
                    }
                }


                val p1 = stringResource(id = R.string.pdf_input_p1)
                Text(
                    text = p1,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Barlow,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.75f),
                            shape = RoundedCornerShape(20)
                        )
                        .bounceClick(
                            onClick = {
                                onPromptSelected(p1)
                            })

                        .padding(10.dp)
                        .fillMaxWidth()

                )


            }

            //  val contex = LocalContext.current

            Image(imageVector = Icons.Outlined.Close, contentDescription = null,
                Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .background(OnSurfaceDark, CircleShape)
                    .padding(3.dp)
                    .click {
                        //val file = Glide.with(contex).asFile().load(imageUri).submit().get()
                        onCancel()
                    })
        }

    }
}


@Preview
@Composable
fun PDFInputPreview(){
    AIVisionTheme {
        PDFInputCard(pdfUri = Uri.parse(""), onPromptSelected = {})
        {

        }
    }
}
