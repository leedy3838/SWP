package com.example.androidstudioproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.setting_qna.*

class SettingQnA : AppCompatActivity() {
    private var mPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_qna)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)

        // 스피너 설정
        qnaQuestion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@SettingQnA, "질문 설정됨", Toast.LENGTH_SHORT).show()
                mPref?.edit()?.run {
                    putString("qnaQuestion", qnaQuestion.selectedItem as String)
                    apply()
                }
                println(mPref?.getString("qnaQuestion","??"))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun qnaSaveClicked(v : View){
        mPref?.edit()?.run {
            putString("qnaAnswer", qnaAnswer.getText().toString())
            apply()
        }
        println(mPref?.getString("qnaAnswer","???"))
        startActivity(Intent(this, BasicScreen::class.java))
    }

}
