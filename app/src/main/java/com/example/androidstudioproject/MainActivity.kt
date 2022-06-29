package com.example.androidstudioproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_age.setOnEditorActionListener{v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                login(v)
                true;
            }
            else false;
        }
    }

    fun login(v : View){
        if(et_email.text.toString() == "ldy@naver.com" && et_password.text.toString() == "1234"
            && et_name.text.toString() == "LDY" && et_age.text.toString() == "23"){
            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "로그인 하지 못했습니다.", Toast.LENGTH_SHORT).show();
    }
}

