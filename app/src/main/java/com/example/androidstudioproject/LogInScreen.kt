package com.example.androidstudioproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.log_in.*
import kotlinx.android.synthetic.main.sign_up.*
import kotlin.system.exitProcess

class LogInScreen : AppCompatActivity() {

    private var mPref : SharedPreferences? = null
    // User 정보들 ( QnA, Grade, UserNamq) 용 SharedPreference

    private var backPressedTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun logInClicked(v: View){
        val pref: SharedPreferences = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        // 첫 접속 체크용 SharedPreference

        val db = FirebaseFirestore.getInstance()
        var logInFlag = false
        lateinit var password : String
        lateinit var qnaQuestion : String
        lateinit var qnaAnswer : String
        lateinit var grade : String

        var sharedPref = getSharedPreferences("appLock", Context.MODE_PRIVATE)
        // 비밀번호 저장을 위한 SharedPreference

        db.collection("user")
            .get()
            .addOnSuccessListener{ col ->
                for(doc in col){
                    val name = doc.id
                    if(log_in_id.getText().toString() == name) {
                        logInFlag = true
                        password = doc.get("password").toString()
                        qnaQuestion = doc.get("QnA_Question").toString()
                        qnaAnswer = doc.get("QnA_Answer").toString()
                        grade = doc.get("grade").toString()
                        break
                    }
                }

                if(logInFlag == true && log_in_pin.getText().toString() == password) {
                    val editor: SharedPreferences.Editor = pref.edit()
                    editor.putBoolean("isFirst", false)
                    editor.apply()

                    mPref?.edit()?.run {
                        putString("userName", log_in_id.getText().toString())
                        putString("qnaQuestion", qnaQuestion)
                        putString("qnaAnswer", qnaAnswer)
                        putString("userGradeSetting", grade)
                        apply()
                    }

                    sharedPref.edit().apply {
                        putString("applock", password)
                        apply()
                    }

                    startActivity(Intent(this, BasicScreen::class.java))

                }else{
                    log_in_msg.isVisible = true
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }


    }

    fun signUpClicked(v: View){
        startActivity(Intent(this, SignUpScreen::class.java))
    }

    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}