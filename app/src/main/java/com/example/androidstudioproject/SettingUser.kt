package com.example.androidstudioproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.setting_user.*
import kotlinx.android.synthetic.main.setting_user.view.*

class SettingUser : AppCompatActivity() {

    private var mPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.setting_user)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)

        userGradeRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when(checkedId){
                R.id.btn_grade1 -> mPref?.edit()?.run {
                    putString("userGradeSetting", "1학년")
                    apply()
                }
                R.id.btn_grade2 -> mPref?.edit()?.run {
                    putString("userGradeSetting", "2학년")
                    apply()
                }
                R.id.btn_grade3 -> mPref?.edit()?.run {
                    putString("userGradeSetting", "3학년")
                    apply()
                }
            }
        }
    }

    fun userSaveClicked(v : View){
        mPref?.edit()?.run {
            putString("userName", userInit.getText().toString())
            apply()
        }
        println(mPref?.getString("qnaAnswer","?"))
        Toast.makeText(this@SettingUser, "이름 및 학년 설정됨", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SettingPassword::class.java))
    }
}