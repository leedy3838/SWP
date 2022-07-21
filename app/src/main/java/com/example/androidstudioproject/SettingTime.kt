package com.example.androidstudioproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.setting_time.*

class SettingTime :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_time)
        val picker = findViewById<View>(R.id.timePicker) as TimePicker
        picker.setIs24HourView(true)
        saveTimePickerButton.setOnClickListener {
            save(picker,picker.hour,picker.minute)
        }
    }
    fun save(v:View,hour:Int,minute:Int){
        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPref.edit().run{
            putInt("hour",hour)
            putInt("minute",minute)
            commit()
        }

        startActivity(Intent(this, SettingScreen::class.java))
    }
}

