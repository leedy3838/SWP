package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.setting_time.*

class SettingTime :AppCompatActivity() {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_time)
        val picker = findViewById<View>(R.id.timePicker) as TimePicker
        picker.setIs24HourView(true)
        saveTimePickerButton.setOnClickListener {
            save(picker.hour,picker.minute)
        }
    }

    private fun save(hour:Int, minute:Int){
        sharedPref.edit().run{
            putInt("hour",hour)
            putInt("minute",minute)
            commit()
        }

        finish()
    }
}