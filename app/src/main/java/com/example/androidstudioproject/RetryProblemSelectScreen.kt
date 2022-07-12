package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RetryProblemSelectScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retry_problem_select)
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun back(v : View){
        startActivity(Intent(this, RetryProblemScreen::class.java))
    }

    fun toProblemSolve(v : View){
        startActivity(Intent(this, ProblemSolveScreen::class.java))
    }
}