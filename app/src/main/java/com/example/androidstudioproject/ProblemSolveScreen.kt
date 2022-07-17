package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.problem_solve.*

class ProblemSolveScreen :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.problem_solve)

        val db = FirebaseFirestore.getInstance()
        var st = ""

        val docRef = db.collection("고등학생")
            .document("3학년")
            .collection("국어")
            .document("공통")
            .collection("공통")
            .document("문제 1번")

        val docRefMath = db.collection("고등학생")
            .document("3학년")
            .collection("수학")
            .document("공통")
            .collection("공통")
            .document("2022년 3월 모의고사 1번")

        docRef.get()
            .addOnSuccessListener { document ->
                st = document.get("이미지").toString()

                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef: StorageReference = storage.getReference(st)

                Glide.with(this)
                    .load(storageRef)
                        //대기 화면(placeholder)
                    .placeholder(R.drawable.text_background)
                    .into(imageViewProblemSolve)
            }
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toProblemSolveNext(v : View){
        startActivity(Intent(this, ProblemSolveNextScreen::class.java))
    }
}