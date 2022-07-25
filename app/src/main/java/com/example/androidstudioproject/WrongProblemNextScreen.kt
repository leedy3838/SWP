package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WrongProblemNextScreen:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wrong_problem_next)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, WrongProblemScreen::class.java))
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun back(v : View){
        startActivity(Intent(this, WrongProblemScreen::class.java))
    }

    fun toProblemSolve(v : View){
        startActivity(Intent(this, ProblemSolveScreen::class.java))
    }
}