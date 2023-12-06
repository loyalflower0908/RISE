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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StartLogo2(logoComplete:() -> Unit){
    ////리컴포지션 방지 이미지 로드////
    val sunPainter = painterResource(id = R.drawable.sunlogo)
    val sunsetPainter1 = painterResource(id = R.drawable.sunset1)
    val sunsetPainter2 = painterResource(id = R.drawable.sunset2)
    val sunsetPainter3 = painterResource(id = R.drawable.sunset3)
    val sunsetPainter4 = painterResource(id = R.drawable.sunset4)
    val sunsetPainter5 = painterResource(id = R.drawable.sunset5)
    val risePainter = painterResource(id = R.drawable.rise1)

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
    var textFloat2State by remember {
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
    var swingSwitch by remember {
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
    var logoChange by remember {
        mutableStateOf(false)
    }
    var logoChange2 by remember {
        mutableStateOf(false)
    }
    val colorAnimate by animateColorAsState(targetValue = colorState,
        tween(1600, easing = FastOutSlowInEasing),
        label = "color")
    val riseFloatAnimate by animateFloatAsState(
        targetValue = textFloatState,
        animationSpec = tween(1800, easing = FastOutSlowInEasing),
        label = "text"
    )
    val rise2FloatAnimate by animateFloatAsState(
        targetValue = textFloat2State,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "text"
    )
    val paddingAnimate by animateDpAsState(targetValue = paddingState,
        animationSpec = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
        label = "padding")
    val swingAnimate by animateIntAsState(targetValue = swingState,
        animationSpec = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
        label = "swing")
    val swing = 0.0009*((swingAnimate-swingState/2)*(swingAnimate-swingState/2)-(swingState/2)*(swingState/2))
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorAnimate)){
        if(!logoChange){
            Image(painter = sunPainter, contentDescription = "SUN",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = sunYanimate + halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate))
        }
        if(!boxSwitch){
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 460.dp)
                    .size(600.dp, 400.dp)
                    .background(colorAnimate)
            )
        }
        if(!logoChange){
            Image(painter = sunsetPainter1, contentDescription = "SUNSET1",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate)
                    .alpha(sunset1FloatAnimate))
            Image(painter = sunsetPainter2, contentDescription = "SUNSET2",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate)
                    .alpha(sunset2FloatAnimate))
            Image(painter = sunsetPainter3, contentDescription = "SUNSET3",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate)
                    .alpha(sunset3FloatAnimate))
            Image(painter = sunsetPainter4, contentDescription = "SUNSET4",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate)
                    .alpha(sunset4FloatAnimate))
            Image(painter = sunsetPainter5, contentDescription = "SUNSET5",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .size(600.dp - 3.1 * paddingAnimate)
                    .alpha(sunset5FloatAnimate))
        }
        if(logoChange2){
            Image(painter = risePainter, contentDescription = "Rise",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = halfscreenHeight - paddingAnimate)
                    .offset(swing.dp, swingAnimate.dp)
                    .size(600.dp - 3.1 * paddingAnimate - 0.7 * swingAnimate.dp)
                    .alpha(sunset5FloatAnimate))
        }
        if(textAvailable){
            Text(text = "R I S E", fontSize = 40.sp, color = Color(0xFFFFCD4A), fontWeight = FontWeight.ExtraBold, modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 120.dp)
                .alpha(riseFloatAnimate - rise2FloatAnimate))
        }
        LaunchedEffect(key1 = Unit){
            logoChange2 = true
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
            textFloat2State = 1.0f
            colorState = Color.Transparent
            logoChange = true
            logoComplete()
            swingSwitch = true
            boxSwitch = true
            textSwitch = false
        }
        if(swingSwitch){
            swingState = 710
            textAvailable = false
            swingSwitch = false
        }
    }
}