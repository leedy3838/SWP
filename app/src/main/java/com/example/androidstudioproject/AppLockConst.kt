package com.example.androidstudioproject

object AppLockConst {
    val type = "type"
    val INIT_PASSLOCK = 1  // 처음 잠금 초기화
    val ENABLE_PASSLOCK = 2  // 비밀번호 분실 시 초기화
    val CHANGE_PASSWORD = 3 // 비밀번호 변경
    val UNLOCK_PASSWORD = 4 // 잠금해제
}