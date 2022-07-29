package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.problem_solve_next.*

class ProblemSolveNextScreen : AppCompatActivity() {
    lateinit var sendintent : Intent

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

    fun addToRetry(v : View){
        val db = FirebaseFirestore.getInstance()

        val user = intent.getStringExtra("user").toString()
        val problem = intent.getStringExtra("문제 정보").toString()
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()

        val name = grade+" "+subject+" "+problem

        val retryRef = db.collection("다시 풀기").document(user).collection(user)

        val data = hashMapOf(
            "학년" to grade,
            "과목" to subject,
            "문제 정보" to problem
        )
        retryRef.document(name).set(data)
    }
}