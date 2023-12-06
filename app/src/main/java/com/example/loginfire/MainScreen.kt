package com.example.loginfire

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    isStart:Boolean,
    logoOff:()->Unit,
    finish: () -> Unit,
    context: Context,
    QRPage:()->Unit,
    logoUploadPage: ()->Unit,
    galleryPage:()->Unit){
    val mainScreenBack = painterResource(id = R.drawable.mainscreenback)
    val cloud = painterResource(id = R.drawable.image10)
    val ques1 = painterResource(id = R.drawable.ques1)
    val ques2 = painterResource(id = R.drawable.ques2)
    val ybutton = painterResource(id = R.drawable.yellowbutton)
    val galleryImage = painterResource(id = R.drawable.galleryimage)
    val qrImage = painterResource(id = R.drawable.qrimage)
    val cameraImage = painterResource(id = R.drawable.cameraimage)
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val cloudAnimation = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = 24.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            tween(
                1600,
                easing =  FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "fly"
    )
    var compoLoading by remember {
        mutableStateOf(false)
    }
    if(isStart){
        logoOff()
        compoLoading = true
    }
    //waitTime = 뒤로가기 버튼 한 번 누르고 얼마안에 다시 입력해야 종료할 지 BackHandler로 관리
    var waitTime by remember { mutableStateOf(0L) }
    BackHandler(enabled = true, onBack = {
        if (System.currentTimeMillis() - waitTime >= 1600) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(context, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
                .show()
        } else {
            finish()
        }
    })
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)){
            this@Column.AnimatedVisibility(visible = compoLoading,
                enter = slideInVertically(animationSpec = tween(2000, 1200), initialOffsetY = {-it})
            ) {
                Image(painter = mainScreenBack,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .height(472.dp),
                    contentDescription = "mainback")
            }
            this@Column.AnimatedVisibility(visible = compoLoading, modifier = Modifier.align(
                Alignment.TopCenter),
                enter = slideInVertically(animationSpec = tween(1600, 2200), initialOffsetY = {it/8}) + fadeIn(animationSpec = tween(1600, 2200))
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp
                        )
                        ){
                            append("우리의 기업이\n" +
                                    "세상에 나아가는 날까지\n")
                        }
                        withStyle(style = SpanStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(900),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp
                        )
                        ){
                            append("라이즈가")
                        }
                        withStyle(style = SpanStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp
                        )
                        ){
                            append("\n" +
                                    "도와드립니다")
                        }
                    }, lineHeight = 36.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 0.dp, top = 88.dp))
                Box(modifier = Modifier
                    .padding(top = 372.dp)
                    .align(Alignment.TopCenter)
                    .size(360.dp, 108.dp)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(Color(0xFFFFF4AE)),
                ){
                    Image(painter = painterResource(id = R.drawable.image20),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .align(Alignment.CenterStart)
                            .size(64.dp),
                        contentDescription = "brandTag")
                    Column(modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.Center)) {
                        Text(
                            text = "유명한 브랜드 이야기",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(200),
                                color = Color(0xFF141414),
                                letterSpacing = 0.36.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "브랜드란 무엇일까요?",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),

                                letterSpacing = 0.36.sp,
                            )
                        )
                    }
                }
            }
            this@Column.AnimatedVisibility(visible = compoLoading, modifier = Modifier.align(
                Alignment.TopEnd),
                enter = slideInHorizontally(animationSpec = tween(2000, 2200), initialOffsetX = {it/6}) + fadeIn(animationSpec = tween(2000, 2200))
            ) {
                Image(painter = cloud, modifier = Modifier
                    .padding(top = 120.dp, end = 8.dp)
                    .padding(vertical = cloudAnimation.value)
                    .size(280.dp), contentDescription = "cloud")
            }
        }
        AnimatedVisibility(visible = compoLoading,
            enter = slideInVertically(animationSpec = tween(1600, 2600), initialOffsetY = {it/4}) + fadeIn(animationSpec = tween(1600, 2600))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "브랜드 제작소",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    ),
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .border(
                            width = 0.dp,
                            color = Color(0xFFA9A9A9),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .width(360.dp)
                        .height(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        ).clickable {
                            logoUploadPage()
                        }) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(painter = cameraImage,
                        modifier = Modifier
                            .size(40.dp),
                        contentDescription = "cameraimage")
                    Spacer(modifier = Modifier.width(32.dp))
                    Column(modifier = Modifier.width(160.dp)) {
                        Text(
                            text = "이미지 분석",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),

                                letterSpacing = 0.36.sp,
                            )
                        )
                        Text(
                            text = "하루 10개 무료 진행",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(300),
                                color = Color(0xFF141414),

                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(modifier = Modifier
                        .width(64.dp)
                        .height(48.dp)){
                        Image(painter = ybutton,
                            contentDescription = "ybutton",
                            modifier = Modifier.size(64.dp, 48.dp))
                        Text(
                            text = "OPEN",
                            style = TextStyle(
                                fontSize = 10.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(800),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 0.2.sp,
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .border(
                            width = 0.dp,
                            color = Color(0xFFA9A9A9),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .width(360.dp)
                        .height(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        ).clickable {
                            QRPage()
                        }) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(painter = qrImage,
                        modifier = Modifier
                            .size(40.dp),
                        contentDescription = "qrimage")
                    Spacer(modifier = Modifier.width(32.dp))
                    Column(modifier = Modifier.width(160.dp)) {
                        Text(
                            text = "QR 제작소",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),

                                letterSpacing = 0.36.sp,
                            )
                        )
                        Text(
                            text = "최초 1회 무료 제작 진행",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(300),
                                color = Color(0xFF141414),

                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(modifier = Modifier
                        .width(64.dp)
                        .height(48.dp)){
                        Image(painter = ybutton,
                            contentDescription = "ybutton",
                            modifier = Modifier.size(64.dp, 48.dp))
                        Text(
                            text = "OPEN",
                            style = TextStyle(
                                fontSize = 10.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(800),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 0.2.sp,
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .border(
                            width = 0.dp,
                            color = Color(0xFFA9A9A9),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .width(360.dp)
                        .height(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        ).clickable {
                            galleryPage()
                        }) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(painter = galleryImage,
                        modifier = Modifier
                            .size(40.dp),
                        contentDescription = "galleyimage")
                    Spacer(modifier = Modifier.width(32.dp))
                    Column(modifier = Modifier.width(160.dp)) {
                        Text(
                            text = "나만의 갤러리",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),

                                letterSpacing = 0.36.sp,
                            )
                        )
                        Text(
                            text = "갤러리 모음집",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(300),
                                color = Color(0xFF141414),

                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(modifier = Modifier
                        .width(64.dp)
                        .height(48.dp)){
                        Image(painter = ybutton,
                            contentDescription = "ybutton",
                            modifier = Modifier.size(64.dp, 48.dp))
                        Text(
                            text = "OPEN",
                            style = TextStyle(
                                fontSize = 10.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(800),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 0.2.sp,
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "도움말",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),
                                letterSpacing = 0.36.sp,
                            ),
                            modifier = Modifier
                                .padding(start = 40.dp)
                                .align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier
                            .shadow(
                                elevation = 5.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000)
                            )
                            .border(
                                width = 0.dp,
                                color = Color(0xFFA9A9A9),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .width(360.dp)
                            .height(80.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(
                                color = Color(0xFFFFD900),
                                shape = RoundedCornerShape(size = 10.dp)
                            )) {
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                Text(
                                    text = "궁금하거나 어려운 점이 있으신가요?",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        lineHeight = 25.2.sp,
                                        fontFamily = FontFamily(Font(R.font.pretendard)),
                                        fontWeight = FontWeight(700),
                                        color = Color(0xFF141414),

                                        letterSpacing = 0.36.sp,
                                    )
                                )
                                Text(
                                    text = "도움말 보기기",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 22.4.sp,
                                        fontFamily = FontFamily(Font(R.font.pretendard)),
                                        fontWeight = FontWeight(300),
                                        color = Color(0xFF141414),

                                        letterSpacing = 0.32.sp,
                                    )
                                )
                            }
                        }
                    }
                    Image(painter = ques1,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(96.dp)
                            .align(Alignment.TopEnd),
                        contentDescription = "ques1")
                    Image(painter = ques2,
                        modifier = Modifier
                            .padding(2.dp, 26.dp)
                            .size(80.dp)
                            .align(Alignment.TopEnd),
                        contentDescription = "ques2")
                }
                Spacer(modifier = Modifier.height(130.dp))
            }
        }
    }
}
