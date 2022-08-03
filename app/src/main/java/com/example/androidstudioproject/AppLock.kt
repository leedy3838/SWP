package com.example.androidstudioproject

import android.content.Context

class AppLock(context: Context) {

    private var sharedPref = context.getSharedPreferences("appLock", Context.MODE_PRIVATE)

    // 잠금 비밀번호 저장
    fun setPassLock(password : String){
        sharedPref.edit().apply{
            putString("applock", password)
            apply()
        }
    }

    // 잠금 설정 제거
    fun removePassLock(){
        sharedPref.edit().apply{
            remove("applock")
            apply()
        }
    }

    // 비밀번호가 맞는지 체크
    fun checkPassLock(password: String): Boolean {
        return sharedPref.getString("applock","0") == password
    }

}