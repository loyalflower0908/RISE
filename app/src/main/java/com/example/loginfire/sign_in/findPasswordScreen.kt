//package com.example.loginfire.sign_in
//
//fun pass(){
//    val auth = FirebaseAuth.getInstance()
//
//    auth.sendPasswordResetEmail(email)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // 비밀번호 재설정 이메일 전송 성공
//                Toast.makeText(
//                    applicationContext,
//                    "비밀번호 재설정 이메일을 보냈습니다.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                // 비밀번호 재설정 이메일 전송 실패
//                val errorCode = (task.exception as FirebaseAuthException).errorCode
//                val errorMessage = task.exception?.message
//                Toast.makeText(
//                    applicationContext,
//                    "비밀번호 재설정 이메일 전송 실패: $errorCode, $errorMessage",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//}