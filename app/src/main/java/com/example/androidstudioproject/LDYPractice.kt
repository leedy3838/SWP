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

        val students = db.collection("고등학생")
            .document("3학년")
            .collection("수학")
            .document("확률과 통계")
            .collection("확률과 통계")


        val data23 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 23번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2022년 6월 모의고사 23번").set(data23)

        val data24 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 24번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 1
        )
        students.document("2022년 6월 모의고사 24번").set(data24)
        val data25 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 25번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2022년 6월 모의고사 25번").set(data25)

        val data26 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 26번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2022년 6월 모의고사 26번").set(data26)

        val data27 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 27번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2022년 6월 모의고사 27번").set(data27)

        val data28 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 28번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2022년 6월 모의고사 28번").set(data28)

        val data29 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 29번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 115
        )
        students.document("2022년 6월 모의고사 29번").set(data29)

        val data30 = hashMapOf(
            "출제 년도" to "2022년 6월 모의고사",
            "경로" to "/3학년/수학/확률과 통계/2022년 6월 모의고사/2022년 6월 모의고사 30번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 9
        )
        students.document("2022년 6월 모의고사 30번").set(data30)


        var st = ""

        val docRef = students.document("2022년 6월 모의고사 25번")

        docRef.get()
            .addOnSuccessListener { document ->
                st = document.get("경로").toString()
            }

        btnLDY.setOnClickListener {
            val storage : FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef : StorageReference = storage.getReference(st)

            Glide.with(btnLDY).load(storageRef).into(imageViewLDY)
        }
    }
}
