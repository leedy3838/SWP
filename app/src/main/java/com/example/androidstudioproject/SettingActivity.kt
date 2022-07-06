package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreenActivity::class.java))
    }
}