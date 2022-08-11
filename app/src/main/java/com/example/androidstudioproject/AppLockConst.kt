package com.example.androidstudioproject

object AppLockConst {
    const val type = "type"
    const val INIT_PASSLOCK = 1  // 처음 잠금 초기화
    const val ENABLE_PASSLOCK = 2  // 비밀번호 분실 시 초기화
    const val CHANGE_PASSWORD = 3 // 비밀번호 변경
    const val UNLOCK_PASSWORD = 4 // 잠금해제
}