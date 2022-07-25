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
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.problem_solve.*

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

        val db = FirebaseFirestore.getInstance()
        var st = ""

        val docRef = db.collection("고등학생")
            .document("3학년")
            .collection("국어")
            .document("공통")
            .collection("공통")
            .document("2022년 3월 모의고사 30번")

        val docRefMath = db.collection("고등학생")
            .document("3학년")
            .collection("수학")
            .document("공통")
            .collection("공통")
            .document("2022년 3월 모의고사 1번")

        docRef.get()
            .addOnSuccessListener { document ->
                st = document.get("경로").toString()

                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef: StorageReference = storage.getReference(st)

                Glide.with(this)
                    .load(storageRef)
                    //대기 화면(placeholder)
                    .placeholder(R.drawable.text_background)
                    .into(imageViewProblemSolve)
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