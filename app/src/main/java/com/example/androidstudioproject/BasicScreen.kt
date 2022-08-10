package com.example.androidstudioproject


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.system.exitProcess
import androidx.preference.PreferenceManager
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class BasicScreen : AppCompatActivity() {
    lateinit var user: String
    lateinit var grade: String
    lateinit var subject: String
    lateinit var difficulty: String
    lateinit var selectSubject: String
    var answerRate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref: SharedPreferences = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)

/*
        // 처음 설정을 두번째 실행 이후에도 실행시키고 싶다면 이 코드 활성화시키고
        // run을 한번한 후에 다시 주석 처리 후 run하면 된다.
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("isFirst", true)
        editor.commit()
*/

        var first: Boolean = pref.getBoolean("isFirst", true)

        println("isFirst = " + first)

        if (first == true) {
            setContentView(R.layout.activity_app_lock_password)
            startActivity(Intent(this, SettingUser::class.java))
            //앱 최초 실행시 사용자 이름 설정 액티비티로 이동

        } else {
            setContentView(R.layout.basic_screen)
            Log.d("Is first Time?", "not first")


            val db = FirebaseFirestore.getInstance()
            var st = ""

            val docRef = db.collection("user")
            var exist = false

            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this)

            user = sharedPreferences.getString("userName", "default").toString()
            grade = sharedPreferences.getString("userGradeSetting", "").toString()
            subject = sharedPreferences.getString("subject", "없음").toString()
            difficulty = sharedPreferences.getString("difficulty", "Easy").toString()
            selectSubject = sharedPreferences.getString("detailSubject", "없음").toString()

            docRef
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == user)
                            exist = true;
                    }
                    if (!exist) {
                        val data = hashMapOf("user" to user)
                        docRef.document(user).set(data)

                        val base = hashMapOf("base" to "yes")

                        db.collection("다시 풀기")
                            .document(user)
                            .set(data)
                        db.collection("다시 풀기")
                            .document(user)
                            .collection(user)
                            .document("기본 문서")
                            .set(base)

                        db.collection("오늘 푼 문제")
                            .document(user)
                            .set(data)
                        db.collection("오늘 푼 문제")
                            .document(user)
                            .collection(user)
                            .document("기본 문서")
                            .set(base)

                        db.collection("틀린 문제")
                            .document(user)
                            .set(data)
                        db.collection("틀린 문제")
                            .document(user)
                            .collection(user)
                            .document("기본 문서")
                            .set(base)
                    }
                }
        }

        var sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this@BasicScreen)

        var hour = sharedPreferences.getInt("hour", 0).toString()
        var minute = sharedPreferences.getInt("minute", 0).toString()
        var second = sharedPreferences.getInt("second", 0).toString()
        if (hour.length == 1) hour = "0" + hour
        if (minute.length == 1) minute = "0" + minute
        if (second.length == 1) second = "0" + second
        var conversionTime = hour + minute + second
        countDown(conversionTime)

        var userInfo = findViewById<TextView>(R.id.userInfo)
        val userName = sharedPreferences.getString("userName", "?")
        val userGrade = sharedPreferences.getString("userGradeSetting", "?")
        userInfo.setText(userName + " / " + userGrade)

        var userInfo2 = findViewById<TextView>(R.id.userInfo2)
        val subject = sharedPreferences.getString("subject", "?")
        val detailSubject = sharedPreferences.getString("detailSubject", subject)
        val difficulty = sharedPreferences.getString("userGradeSetting", "?")
        userInfo2.bringToFront()
        userInfo2.setText(detailSubject + " / " + difficulty)
    }

    fun countDown(time: String) {
        var leftTime = findViewById<TextView>(R.id.leftTime)
        var conversionTime: Long = 0

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        var getHour = time.substring(0, 2)
        var getMin = time.substring(2, 4)
        var getSecond = time.substring(4, 6)

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2)
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2)
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2)
        }

        // 변환시간
        conversionTime =
            getHour.toLong() * 1000 * 3600 + getMin.toLong() * 60 * 1000 + getSecond.toLong() * 1000

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        object : CountDownTimer(conversionTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // 시간단위
                var hour = (millisUntilFinished / (60 * 60 * 1000)).toString()

                // 분단위
                val getMin: Long = millisUntilFinished - (hour.toLong() * 60 * 60 * 1000)
                var min = (getMin / (60 * 1000)).toString() // 몫

                // 초단위
                var second = ((getMin % (60 * 1000)) / 1000).toString() // 나머지

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@BasicScreen)
                sharedPref.edit().run {
                    putInt("hour", hour.toInt())
                    putInt("minute", min.toInt())
                    putInt("second", second.toInt())
                    commit()
                }

                // 시간이 한자리면 0을 붙인다
                if (hour.length == 1) {
                    hour = "0$hour"
                }

                // 분이 한자리면 0을 붙인다
                if (min.length == 1) {
                    min = "0$min"
                }

                // 초가 한자리면 0을 붙인다
                if (second.length == 1) {
                    second = "0$second"
                }

                leftTime.setText("$hour:$min:$second")
            }

            override fun onFinish() {
                leftTime.setText("시간종료!")
            }

        }.start()

    }



    private var backPressedTime : Long = 0

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



    fun retryProblemClicked(v : View){
        val intent = Intent(this, RetryProblemScreen::class.java)
        intent.putExtra("user", user)

        startActivity(intent)
    }

    fun settingClicked(v : View){
        startActivity(Intent(this, SettingScreen::class.java))
    }

    fun helpClicked(v : View){
        startActivity(Intent(this, HelpScreen::class.java))
    }

    fun problemSolveClicked(v : View){
        val intent = Intent(this, ProblemSolveScreen::class.java)

        intent.putExtra("user", user) // 닉네임
        intent.putExtra("학년", grade) // 학년
        intent.putExtra("과목", subject) // 과목
        //문제 정보가 없음이면 basicScreen에서 문제 풀기를 실행한 것이므로 시간 추가
        intent.putExtra("문제 정보", "없음")
        intent.putExtra("세부과목", selectSubject) // 세부과목 디폴트값 "없음"

        if (difficulty == "Easy")
            answerRate = 100L
        else if (difficulty == "Normal")
            answerRate = 60L
        else if (difficulty == "Hard")
            answerRate = 30L

        intent.putExtra("정답률", answerRate) // 초기 정답률

        if (subject == "없음"){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("설정에서 과목을 선택해주십시오.")
                .setPositiveButton("확인") { _, _ ->

                }
            val alertDialog = builder.create()
            val window = alertDialog.window
            window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }
        else startActivity(intent)
    }

    fun todaySolveClicked(v : View){
        val intent = Intent(this, TodaySolveScreen::class.java)
        intent.putExtra("user", user)

        startActivity(Intent(intent))
    }

    fun wrongProblemClicked(v : View){
        val intent = Intent(this, WrongProblemScreen::class.java)
        intent.putExtra("user", user)

        startActivity(intent)
    }
}