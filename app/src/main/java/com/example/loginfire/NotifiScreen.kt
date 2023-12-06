package com.example.loginfire

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotifiScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Image(painter = painterResource(id = R.drawable.bell),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(120.dp , 80.dp),
            contentDescription = "벨")
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "알림",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 25.2.sp,
                fontFamily = FontFamily(Font(R.font.pretendard)),
                fontWeight = FontWeight(700),
                color = Color(0xFF141414),
                letterSpacing = 0.36.sp,
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
        Box(modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(120.dp)
            .align(Alignment.CenterHorizontally)
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
            .clip(RoundedCornerShape(size = 10.dp))
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable {
            })
        {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.qrimage1),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(90.dp)
                        .clip(
                            RoundedCornerShape(20)
                        ),
                    contentDescription = "qr1")
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "당신만의 특별한 QR이\n생성되었습니다!",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.Clear,
                    tint = Color.LightGray,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(120.dp)
            .align(Alignment.CenterHorizontally)
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
            .clip(RoundedCornerShape(size = 10.dp))
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable {
            })
        {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.qrimage2),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(90.dp)
                        .clip(
                            RoundedCornerShape(20)
                        ),
                    contentDescription = "qr2")
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "당신만의 특별한 QR이\n생성되었습니다!",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.Clear,
                    tint = Color.LightGray,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(120.dp)
            .align(Alignment.CenterHorizontally)
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
            .clip(RoundedCornerShape(size = 10.dp))
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable {
            })
        {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.qrimage3),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(90.dp)
                        .clip(
                            RoundedCornerShape(20)
                        ),
                    contentDescription = "qr3")
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "당신만의 특별한 QR이\n생성되었습니다!",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.Clear,
                    tint = Color.LightGray,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "")
            }
        }
    }
}