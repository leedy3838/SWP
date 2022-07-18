package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SettingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.setting_screen, SettingFragment())
            .commit()
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }
}