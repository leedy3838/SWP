package com.example.androidstudioproject

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.unlock_qna.*

class UnlockQnA : AppCompatActivity() {

    private var mPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)

        val intent = intent
        intent.putExtra("QnAFlag", "noPass")

        setContentView(R.layout.unlock_qna)

        tvQnAQuestion.setText(mPref?.getString("qnaQuestion","?"))
        tvQnAQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        println(mPref?.getString("qnaAnswer","?"))
        if(!(etQnAAnswer.text.toString().equals(mPref?.getString("qnaAnswer","??")))){
            btnPasswordInit.setEnabled(false)
        }

        etQnAAnswer.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(etQnAAnswer.text.toString().equals(mPref?.getString("qnaAnswer","??")))
                {
                    println("답변 통과")
                    btnPasswordInit.setEnabled(true)
                }
                else {
                    println("답변 다름")
                    btnPasswordInit.setEnabled(false)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    fun btnPasswordInitclicked(v : View){
        val intent = intent
        intent.putExtra("QnAFlag", "Pass")
        Toast.makeText(this@UnlockQnA, "질문/답 통과", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun btnPasswordInitCancelclicked(v : View){
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}

