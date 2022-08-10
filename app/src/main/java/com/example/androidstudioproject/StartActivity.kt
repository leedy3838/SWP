package com.example.androidstudioproject

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if(!checkAccessibilityPermissions()) {
            setAccessibilityPermissions()
        }
        else {
            val intent = Intent(getApplicationContext(),AccessibilityService::class.java)
            startService(intent)
            // 일정 시간 지연 이후 실행하기 위한 코드
            Handler(Looper.getMainLooper()).postDelayed({
                // 일정 시간이 지나면 메인화면으로 이동
                val intent = Intent(this, BasicScreen::class.java)
                startActivity(intent)

                // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
                // 이동한 다음 사용안함으로 finish 처리
                finish()

            }, 1500) // 시간 1.5초 이후 실행
        }

    }

    fun checkAccessibilityPermissions(): Boolean {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        // getEnabledAccessibilityServiceList는 현재 접근성 권한을 가진 리스트를 가져오게 된다
        val list =
            accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT)
        for (i in list.indices) {
            val info = list[i]

            // 접근성 권한을 가진 앱의 패키지 네임과 패키지 네임이 같으면 현재앱이 접근성 권한을 가지고 있다고 판단함
            if (info.resolveInfo.serviceInfo.packageName == application.packageName) {
                return true
            }
        }
        return false
    }
    fun setAccessibilityPermissions() {
        Log.i(TAG, "The user may not allow the access to apps usage. ")
        Toast.makeText(
            this,
            "Failed to retrieve app usage statistics. " +
                    "You may need to enable access for this app through " +
                    "Settings > Security > Apps with usage access",
            Toast.LENGTH_LONG
        ).show()
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}