package com.example.loginfire

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoResultScreen(
    caption:String,
    back:()->Unit,
    makeQROn:(String)->Unit,
    uriSender:(Uri) -> Unit,
    uri:Uri
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF8F8F8))){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopStart)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = 32.dp, // 하단 시작 부분
                            bottomEnd = 32.dp // 하단 끝 부분
                        )
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFD900), // 시작 색상
                                Color(0xFFECDB7A)  // 끝 색상
                            )
                        )
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.left),
                    tint = Color.White,
                    modifier = Modifier
                        .padding(0.dp, 60.dp)
                        .size(64.dp, 28.dp)
                        .clickable(onClick = back),
                    contentDescription = "leftIcon"
                )
                Text(
                    text = "이미지 분석 결과",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "현재 이미지에 대한\n프롬프트 분석 값",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 25.2.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    letterSpacing = 0.36.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "분석 내용",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF141414),
                    letterSpacing = 0.32.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (caption.isNotEmpty()) {
                val parts = caption.split('/')
                TextSection(title = "1. 텍스트 추출 :", text = parts.getOrNull(0) ?: "")
                TextSection(title = "2. 색상 의미 :", text = parts.getOrNull(1) ?: "")
                TextSection(title = "3. 텍스트 추출 번역 :", text = parts.getOrNull(2) ?: "")
                TextSection(title = "4. 색상 의미 번역 :", text = parts.getOrNull(3) ?: "")
            }
        }
        Button(
            onClick = {
            },
            modifier = Modifier
                .padding(40.dp, 56.dp)
                .width(160.dp)
                .height(52.dp)
                .shadow(
                    elevation = 5.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.BottomStart),
            shape = RoundedCornerShape(size=10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD900),
                disabledContainerColor = Color(0xFFE4E4E4)
            )
        ) {
            Text(
                text = "돌아가기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Button(
            onClick = {
                if (caption.isNotEmpty()) {
                    makeQROn(caption)
                    uriSender(uri)
                }
            },
            modifier = Modifier
                .padding(40.dp, 56.dp)
                .width(160.dp)
                .height(52.dp)
                .shadow(
                    elevation = 5.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.BottomEnd),
            shape = RoundedCornerShape(size=10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD900),
                disabledContainerColor = Color(0xFFE4E4E4)
            ),
            enabled = caption.isNotEmpty()
        ) {
            Text(
                text = "QR 제작 하기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun TextSection(title: String, text: String) {
    Column(modifier = Modifier.padding(8.dp, 16.dp)) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(R.font.pretendard)),
                fontWeight = FontWeight(400),
                color = Color(0xFF141414),
                letterSpacing = 0.24.sp,
            ),
            modifier = Modifier.padding(20.dp, 0.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(R.font.pretendard)),
                fontWeight = FontWeight(400),
                color = Color(0xFF141414),
                letterSpacing = 0.24.sp,
            ),
            modifier = Modifier.padding(28.dp, 0.dp)
        )
    }
}