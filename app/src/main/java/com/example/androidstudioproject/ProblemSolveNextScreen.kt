package com.example.androidstudioproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore

class ProblemSolveNextScreen : AppCompatActivity() {
    private lateinit var sendintent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.problem_solve_next)

        val user = intent.getStringExtra("user")
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val selectSubject = intent.getStringExtra("세부과목").toString()
        val answerRate : Long = intent.getLongExtra("정답률", 100)

        println(grade)
        println(subject)
        println(answerRate)

        sendintent = Intent(this, ProblemSolveScreen::class.java)

        sendintent.putExtra("user", user)
        sendintent.putExtra("정답률", answerRate)
        sendintent.putExtra("학년", grade)
        sendintent.putExtra("과목", subject)
        sendintent.putExtra("세부과목", selectSubject)
        
        sendintent.putExtra("문제 정보", "없음")

        var sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this@ProblemSolveNextScreen)

        var hour = sharedPreferences.getInt("hour", 0).toString()
        var minute = sharedPreferences.getInt("minute", 0).toString()
        var second = sharedPreferences.getInt("second", 0).toString()
        if (hour.length == 1) hour = "0" + hour
        if (minute.length == 1) minute = "0" + minute
        if (second.length == 1) second = "0" + second
        var conversionTime = hour + minute + second
        countDown(conversionTime)
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

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@ProblemSolveNextScreen)
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

    override fun onBackPressed() {
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toProblemSolve(v : View){
        startActivity(sendintent)
    }

    fun addToRetry(v : View) {
        val db = FirebaseFirestore.getInstance()

        val user = intent.getStringExtra("user").toString()
        val problem = intent.getStringExtra("문제 정보").toString()
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val detailSubject = intent.getStringExtra("세부과목").toString()

        val docRef = db.collection("다시 풀기")
            .document(user)
            .collection(user)

        val name: String
        if (detailSubject == "없음")
            name = "$grade $subject $problem"
        else
            name = "$grade $subject $detailSubject $problem"

        docRef
            .get()
            .addOnSuccessListener { result ->
                var flag = false
                for (document in result) {
                    if (document.id == name) {
                        flag = true
                        val toast = Toast.makeText(this, "이미 존재하는 문제입니다.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.BOTTOM, 0, 200)
                        toast.show()
                        break
                    }
                }

                if (!flag) {
                    val toast = Toast.makeText(this, "다시 풀어보고 싶은 문제에 추가하였습니다.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 0, 200)
                    toast.show()
                    val retryRef = db.collection("다시 풀기").document(user).collection(user)

                    val data = hashMapOf(
                        "학년" to grade,
                        "과목" to subject,
                        "문제 정보" to problem,
                        "세부과목" to detailSubject
                    )
                    retryRef.document(name).set(data)

                }
            }
    }
}