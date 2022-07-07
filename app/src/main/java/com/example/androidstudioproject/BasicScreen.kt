package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BasicScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_screen)
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
}