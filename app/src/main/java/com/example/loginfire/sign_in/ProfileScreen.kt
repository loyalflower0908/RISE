package com.example.loginfire.sign_in

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loginfire.R

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    isStart: Boolean
) {
    //isStart
    if(isStart){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            if(userData?.profilePictureUrl != null){
                AsyncImage(model = userData.profilePictureUrl,
                    contentDescription = "프로필 사진",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if(userData?.username != null){
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(36.dp))
            }
            Row {
                Box(modifier = Modifier
                    .width(96.dp)
                    .height(80.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(color = Color(0xFFFFED79), shape = RoundedCornerShape(size = 10.dp))){
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = "Point",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                letterSpacing = 0.32.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "30,000 P",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Box(modifier = Modifier
                    .width(96.dp)
                    .height(80.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(color = Color(0xFFFFED79), shape = RoundedCornerShape(size = 10.dp))){
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = "Orders",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                letterSpacing = 0.32.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "10 건",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Box(modifier = Modifier
                    .width(96.dp)
                    .height(80.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(color = Color(0xFFFFED79), shape = RoundedCornerShape(size = 10.dp))){
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = "Complite",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                letterSpacing = 0.32.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "2 건",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF141414),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.28.sp,
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(56.dp))

            Box(modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
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
                Icon(painter = painterResource(id = R.drawable.coupon),
                    contentDescription = "Coupon",
                    tint = Color(0xFF141414),
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                )
                Text(
                    text = "쿠폰 목록",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
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
                Icon(painter = painterResource(id = R.drawable.mooni),
                    contentDescription = "mooni",
                    tint = Color(0xFF141414),
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "문의 목록",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
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
                Icon(painter = painterResource(id = R.drawable.pay),
                    contentDescription = "pay",
                    tint = Color(0xFF141414),
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "결제 목록",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onSignOut, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Text(
                    text = "로그아웃",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(600),
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}