package com.example.loginfire.sign_in

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfire.R
import com.example.loginfire.toastMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(context: Context, signUpDone:() -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nickname by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고나 타이틀 등의 다른 UI 요소 추가 가능

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "이메일 입력",
            fontFamily = FontFamily(Font(R.font.pretendbold)),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 48.dp))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "이메일") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFBFBFB),
                focusedTextColor = Color(0xFF141414),
                unfocusedTextColor = Color(0xFF141414),
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(44.dp))
        Text(text = "비밀번호 입력",
            fontFamily = FontFamily(Font(R.font.pretendbold)),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 48.dp))
        Spacer(modifier = Modifier.height(8.dp))
        // 비밀번호 입력 필드
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "비밀번호") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFBFBFB),
                focusedTextColor = Color(0xFF141414),
                unfocusedTextColor = Color(0xFF141414),
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 비밀번호 확인 필드
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "비밀번호 확인") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFBFBFB),
                focusedTextColor = Color(0xFF141414),
                unfocusedTextColor = Color(0xFF141414),
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(44.dp))
        Text(text = "닉네임 입력",
            fontFamily = FontFamily(Font(R.font.pretendbold)),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 48.dp))
        Spacer(modifier = Modifier.height(8.dp))
        // 닉네임 입력 필드
        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text(text = "닉네임") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFBFBFB),
                focusedTextColor = Color(0xFF141414),
                unfocusedTextColor = Color(0xFF141414),
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(72.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(360.dp)
                .height(48.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(percent = 10),
                    spotColor = Color.Black,
                    ambientColor = Color.Black
                )
                .background(Color(0xFFFFD900))
                .clip(RoundedCornerShape(10))
                .clickable(onClick =
                if (email == "" || password == "" || nickname == "") {
                    {
                        toastMessage(context, "비어있는 칸이 있습니다")
                    }
                } else if (password == confirmPassword) {
                    {
                        onSignUpDone(email, password, signUpDone, context, nickname)
                        keyboardController?.hide()
                    }
                } else {
                    {
                        toastMessage(context, "비밀번호가 일치하지 않습니다")
                        isError = true
                    }
                })
        ) {
            Text(
                text = "회원가입",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendbold)),
                color = Color(0xFF141414),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

fun onSignUpDone(email:String, password:String, signUpDone: () -> Unit, context: Context, nickname:String){
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 회원가입 성공 시 실행되는 부분
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid
                val displayName = nickname
                val profilePicUrl = "https://picpac.kr/common/img/default_profile.png"

                // 사용자 정보 업데이트
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profilePicUrl))
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { profileUpdateTask ->
                        if(profileUpdateTask.isSuccessful){
                            signUpDone()
                        }else{
                            toastMessage(context, "회원가입 실패")
                        }
                    }
            } else {
                // 회원가입 실패 시 실행되는 부분
                val errorCode = (task.exception as FirebaseAuthException).errorCode
                if(errorCode == "ERROR_INVALID_EMAIL"){
                    toastMessage(context, "이메일 형식이 올바르지 않습니다")
                }else if(errorCode == "ERROR_WEAK_PASSWORD"){
                    toastMessage(context, "비밀번호가 6자리 이상이어야 합니다")
                }
            }
        }
}