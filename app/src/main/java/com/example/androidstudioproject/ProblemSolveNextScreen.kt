package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.problem_solve_next.*

class ProblemSolveNextScreen : AppCompatActivity() {
    lateinit var sendintent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.problem_solve_next)

        val user = intent.getStringExtra("user")
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val answerRate : Long = intent.getLongExtra("정답률", 100)

        println(grade)
        println(subject)
        println(answerRate)

        sendintent = Intent(this, ProblemSolveScreen::class.java)

        sendintent.putExtra("user", user)
        sendintent.putExtra("정답률", answerRate)
        sendintent.putExtra("학년", grade)
        sendintent.putExtra("과목", subject)
        sendintent.putExtra("문제 정보", "없음")
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
}