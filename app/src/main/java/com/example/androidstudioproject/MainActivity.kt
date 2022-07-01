package com.example.androidstudioproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)

        if(et_name.text.toString() == "LDY" && et_age.text.toString() == "23"){
            Toast.makeText(this, "사용자 확인되었습니다.", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_sub);
        }
        else
            Toast.makeText(this, "로그인 하지 못했습니다", Toast.LENGTH_SHORT).show();
    }
}

