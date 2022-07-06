package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BasicScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_screen)
    }

    fun retryProblemClicked(v : View){
        startActivity(Intent(this, RetryProblem::class.java))
    }

    fun settingClicked(v : View){
        startActivity(Intent(this, SettingActivity::class.java))
    }
}