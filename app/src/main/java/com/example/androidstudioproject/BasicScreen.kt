package com.example.androidstudioproject

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.lang.System.exit


class BasicScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_screen)
    }

    private var backPressedTime : Long = 0

    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }



    fun retryProblemClicked(v : View){
        startActivity(Intent(this, RetryProblemScreen::class.java))
    }

    fun settingClicked(v : View){
        startActivity(Intent(this, SettingScreen::class.java))
    }

    fun helpClicked(v : View){
        startActivity(Intent(this, HelpScreen::class.java))
    }

    fun problemSolveClicked(v : View){
        val intent = Intent(this, ProblemSolveScreen::class.java)

        // 설정에 따라서 나중에 값을 받아주면 됨
        intent.putExtra("정답률", 100)
        intent.putExtra("학년", "1학년")
        intent.putExtra("과목", "과학탐구")

        startActivity(intent)
    }

    fun todaySolveClicked(v : View){
        startActivity(Intent(this, TodaySolveScreen::class.java))
    }

    fun wrongProblemClicked(v : View){
        startActivity(Intent(this, WrongProblemScreen::class.java))
    }
}