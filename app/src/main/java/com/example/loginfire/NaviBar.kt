package com.example.loginfire

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NaviBar(isStart:Boolean, goHome:()->Unit, goChatBot:()->Unit , goNoti:()->Unit, goMypage:() -> Unit, homeSelected:BarState){
    val oneNaviItem = ((LocalConfiguration.current.screenWidthDp)-90)/4
    Box(modifier = Modifier
        .fillMaxSize()){
        AnimatedVisibility(visible = isStart,
            enter = slideInVertically(animationSpec = tween(2000, 1000), initialOffsetY = {it/4}) + fadeIn(animationSpec = tween(2000, 1000)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column {
                Divider(thickness = 1.dp, color = Color.Black.copy(0.1f))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color(0xFFF8F8F8))){
                    Row {
                        Box(modifier = Modifier
                            .width(oneNaviItem.dp)
                            .fillMaxHeight()){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(90.dp)
                                    .clip(
                                        RoundedCornerShape(20)
                                    )
                                    .clickable {
                                        goHome()
                                    }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = if (homeSelected == BarState.HOME) Color(0xff2196f3) else LocalContentColor.current
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "홈",
                                    color = if (homeSelected == BarState.HOME) Color(0xff2196f3) else LocalContentColor.current,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard))
                                )
                            }
                        }
                        Box(modifier = Modifier
                            .width(oneNaviItem.dp)
                            .fillMaxHeight()){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(90.dp)
                                    .clip(
                                        RoundedCornerShape(20)
                                    )
                                    .clickable {
                                        goChatBot()
                                    }) {
                                Icon(
                                    imageVector = Icons.Default.Face,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = if (homeSelected == BarState.CHATBOT) Color(0xff2196f3) else LocalContentColor.current
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "챗봇",
                                    color = if (homeSelected == BarState.CHATBOT) Color(0xff2196f3) else LocalContentColor.current,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard))
                                )
                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .width(90.dp))
                        Box(modifier = Modifier
                            .width(oneNaviItem.dp)
                            .fillMaxHeight()){

                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(90.dp)
                                    .clip(
                                        RoundedCornerShape(20)
                                    )
                                    .clickable {
                                        goNoti()
                                    }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = if (homeSelected == BarState.NOTIFICATION) Color(0xff2196f3) else LocalContentColor.current
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "알림",
                                    color = if (homeSelected == BarState.NOTIFICATION) Color(0xff2196f3) else LocalContentColor.current,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard))
                                )
                            }

                            Badge(modifier = Modifier.padding(16.dp).align(Alignment.TopEnd)) {
                                val badgeNumber = "3"
                                Text(
                                    badgeNumber,
                                )
                            }

                        }
                        Box(modifier = Modifier
                            .width(oneNaviItem.dp)
                            .fillMaxHeight()){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(90.dp)
                                    .clip(
                                        RoundedCornerShape(20)
                                    )
                                    .clickable {
                                        goMypage()
                                    }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = if (homeSelected == BarState.MYPAGE) Color(0xff2196f3) else LocalContentColor.current
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "마이페이지",
                                    color = if (homeSelected == BarState.MYPAGE) Color(0xff2196f3) else LocalContentColor.current,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class BarState{
    HOME,
    CHATBOT,
    NOTIFICATION,
    MYPAGE
}