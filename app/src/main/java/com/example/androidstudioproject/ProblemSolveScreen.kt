package com.example.androidstudioproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.NonCancellable.cancel

class ProblemSolveScreen :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.problem_solve)

        val btn_event = findViewById<Button>(R.id.giveUp)

        btn_event.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문제 포기")
            builder.setMessage("문제를 포기하시겠습니까?")
                .setPositiveButton("포기", DialogInterface.OnClickListener { dialog, which ->
                    startActivity(Intent(this, BasicScreen::class.java))
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    cancel()
                })
            val alertDialog = builder.create()
            val window = alertDialog.window
            window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }
    }
        override fun onBackPressed() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문제 포기")
            builder.setMessage("문제를 포기하시겠습니까?")
                .setPositiveButton("포기", DialogInterface.OnClickListener { dialog, which ->
                    startActivity(Intent(this, BasicScreen::class.java))
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    cancel()
                })
            val alertDialog = builder.create()
            val window = alertDialog.window
            window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toProblemSolveNext(v : View){
        startActivity(Intent(this, ProblemSolveNextScreen::class.java))
    }


}