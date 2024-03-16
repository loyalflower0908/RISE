package com.example.sunlogo

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.loginfire.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//처음 스타트 로고 스플래시 스크린 코드

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StartLogo(logoComplete:() -> Unit, swingSwitch:Boolean){
    ////리컴포지션 방지 이미지 로드////
    val sunPainter = painterResource(id = R.drawable.sunlogo)
    val sunsetPainter1 = painterResource(id = R.drawable.sunset1)
    val sunsetPainter2 = painterResource(id = R.drawable.sunset2)
    val sunsetPainter3 = painterResource(id = R.drawable.sunset3)
    val sunsetPainter4 = painterResource(id = R.drawable.sunset4)
    val sunsetPainter5 = painterResource(id = R.drawable.sunset5)
    //애니메이션용 스위치와 상태 변수들
    var swingSwitch2 by remember {
        mutableStateOf(false)
    }
    if(swingSwitch){
        LaunchedEffect(key1 = Unit){
            swingSwitch2 = true
        }
    }
    val halfscreenHeight = (LocalConfiguration.current.screenHeightDp/2-300).dp
    var sunYState by remember {
        mutableStateOf(320.dp)
    }
    val sunYanimate by animateDpAsState(targetValue = sunYState,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "sunY")
    var sunsetFloatState by remember {
        mutableStateOf(0.0f)
    }
    var textFloatState by remember {
        mutableStateOf(0.0f)
    }
    var text2FloatState by remember {
        mutableStateOf(0.0f)
    }
    var text3FloatState by remember {
        mutableStateOf(0.0f)
    }
    val sunset1FloatAnimate by animateFloatAsState(
        targetValue = sunsetFloatState,
        animationSpec = tween(2200, easing = FastOutSlowInEasing),
        label = ""
    )
    val sunset2FloatAnimate by animateFloatAsState(
        targetValue = sunsetFloatState,
        animationSpec = tween(2800, easing = FastOutSlowInEasing),
        label = ""
    )
    val sunset3FloatAnimate by animateFloatAsState(
        targetValue = sunsetFloatState,
        animationSpec = tween(3400, easing = FastOutSlowInEasing),
        label = ""
    )
    val sunset4FloatAnimate by animateFloatAsState(
        targetValue = sunsetFloatState,
        animationSpec = tween(4600, easing = FastOutSlowInEasing),
        label = ""
    )
    val sunset5FloatAnimate by animateFloatAsState(
        targetValue = sunsetFloatState,
        animationSpec = tween(5800, easing = FastOutSlowInEasing),
        label = ""
    )
    var sunsetSwitch by remember {
        mutableStateOf(false)
    }
    var textSwitch by remember {
        mutableStateOf(false)
    }
    var boxSwitch by remember {
        mutableStateOf(false)
    }
    var sizeSwitch by remember {
        mutableStateOf(false)
    }
    var textAvailable by remember {
        mutableStateOf(true)
    }
    var paddingState by remember {
        mutableStateOf(0.dp)
    }
    var swingState by remember {
        mutableStateOf(0)
    }
    var colorState by remember {
        mutableStateOf(Color(0xff151212))
    }
    val colorAnimate by animateColorAsState(targetValue = colorState,
        tween(1000, easing = FastOutSlowInEasing),
        label = "color")
    val riseFloatAnimate by animateFloatAsState(
        targetValue = textFloatState,
        animationSpec = tween(1800, easing = FastOutSlowInEasing),
        label = "text"
    )
    val rise2FloatAnimate by animateFloatAsState(
        targetValue = text2FloatState,
        animationSpec = tween(2200, easing = FastOutSlowInEasing),
        label = "text"
    )
    val rise3FloatAnimate by animateFloatAsState(
        targetValue = text3FloatState,
        animationSpec = tween(1400,easing = FastOutSlowInEasing),
        label = "text"
    )
    val paddingAnimate by animateDpAsState(targetValue = paddingState,
        animationSpec = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
        label = "padding")
    val swingAnimate by animateIntAsState(targetValue = swingState,
        animationSpec = tween(durationMillis = 2200, delayMillis = 1000, easing = FastOutSlowInEasing),
        label = "swing")
    val swing = 0.0009*((swingAnimate-swingState/2)*(swingAnimate-swingState/2)-(swingState/2)*(swingState/2))
    //화면 구성
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorAnimate)){
        Image(painter = sunPainter, contentDescription = "SUN",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = sunYanimate + halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp))
        if(!boxSwitch){
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 460.dp)
                    .size(600.dp, 400.dp)
                    .background(colorAnimate)
            )
        }
        Image(painter = sunsetPainter1, contentDescription = "SUNSET1",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp)
                .alpha(sunset1FloatAnimate))
        Image(painter = sunsetPainter2, contentDescription = "SUNSET2",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp)
                .alpha(sunset2FloatAnimate))
        Image(painter = sunsetPainter3, contentDescription = "SUNSET3",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp)
                .alpha(sunset3FloatAnimate))
        Image(painter = sunsetPainter4, contentDescription = "SUNSET4",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp)
                .alpha(sunset4FloatAnimate))
        Image(painter = sunsetPainter5, contentDescription = "SUNSET5",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = halfscreenHeight - paddingAnimate)
                .offset(swing.dp, swingAnimate.dp)
                .size(600.dp - 3.1 * paddingAnimate - (swingAnimate / 3).dp)
                .alpha(sunset5FloatAnimate))
        if(textAvailable){
            Text(text = "R I S E", fontSize = 40.sp, color = Color(0xFFFFCD4A), fontWeight = FontWeight.ExtraBold, modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 120.dp)
                .alpha(riseFloatAnimate))
            Text(text = "R I S E", fontSize = 32.sp, color = Color(0xFFFFCD4A), fontWeight = FontWeight.ExtraBold, modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 196.dp)
                .alpha(rise2FloatAnimate - rise3FloatAnimate))
        }
        //애니메이션 관리
        LaunchedEffect(key1 = Unit){
            sunYState = 0.dp
            delay(1500)
            sunsetSwitch = true
        }
        if (sunsetSwitch){
            sunsetFloatState = 1.0f
            textFloatState = 1.0f
            CoroutineScope(Dispatchers.IO).launch {
                delay(800)
                textSwitch = true
            }
            sunsetSwitch = false
        }
        if(textSwitch){
            textFloatState = 0.0f
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
                sizeSwitch = true
            }
            boxSwitch = true
            textSwitch = false
        }
        if(sizeSwitch){
            paddingState = 80.dp
            colorState = Color.Transparent
            CoroutineScope(Dispatchers.IO).launch {
                delay(1800)
                logoComplete()
                text2FloatState = 1.0f
            }
            sizeSwitch = false
        }
        if(swingSwitch2){
            CoroutineScope(Dispatchers.IO).launch {
                text3FloatState = 1.0f
                swingState = 800
                delay(1000)
                textAvailable = false
            }
            swingSwitch2 = false
        }
    }
}
