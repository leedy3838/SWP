package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ProblemSolveScreen :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.problem_solve)
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toProblemSolveNext(v : View){
        startActivity(Intent(this, ProblemSolveNextScreen::class.java))
    }
}