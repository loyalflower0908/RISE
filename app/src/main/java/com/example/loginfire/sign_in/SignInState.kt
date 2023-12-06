package com.example.loginfire.sign_in

data class SignInState(
    val isSignInSuccessful:Boolean = false,
    val signInError:String? = null
)

enum class isNotLogin{
    YET,
    NOT,
    ALREADYLOGIN
}