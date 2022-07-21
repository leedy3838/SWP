package com.example.androidstudioproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.ldy_practice.*

class LDYPractice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ldy_practice)

        val db = FirebaseFirestore.getInstance()

        val students = db.collection("고등학생").document("1학년").collection("과학탐구")

        val data1 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/1학년/과학탐구/2021년 3월 모의고사/2021년 3월 모의고사 1번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2021년 3월 모의고사 1번").set(data1)

        var st : String = ""

        val docRef = students.document("2021년 3월 모의고사 1번")

        docRef.get()
            .addOnSuccessListener { document ->
                st = document.get("경로").toString()
            }

        btnLDY.setOnClickListener {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.getReference(st)

            Glide.with(btnLDY).load(storageRef).into(imageViewLDY)
        }
    }
}
