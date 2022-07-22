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
            .collection("사회탐구")
            .document("생활과 윤리")
            .collection("생활과 윤리")


        val data1 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 1번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2021년 3월 모의고사 1번").set(data1)

        val data2 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 2번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 5
        )
        students.document("2021년 3월 모의고사 2번").set(data2)

        val data3 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 3번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 5
        )
        students.document("2021년 3월 모의고사 3번").set(data3)

        val data4 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 4번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2021년 3월 모의고사 4번").set(data4)

        val data5 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 5번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2021년 3월 모의고사 5번").set(data5)

        val data6 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 6번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2021년 3월 모의고사 6번").set(data6)

        val data7 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 7번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2021년 3월 모의고사 7번").set(data7)

        val data8 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 8번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 1
        )
        students.document("2021년 3월 모의고사 8번").set(data8)

        val data9 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 9번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 1
        )
        students.document("2021년 3월 모의고사 9번").set(data9)

        val data10 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 10번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2021년 3월 모의고사 10번").set(data10)

        val data11 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 11번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2021년 3월 모의고사 11번").set(data11)

        val data12 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 12번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 4
        )
        students.document("2021년 3월 모의고사 12번").set(data12)

        val data13 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 13번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 1
        )
        students.document("2021년 3월 모의고사 13번").set(data13)

        val data14 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 14번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2021년 3월 모의고사 14번").set(data14)

        val data15 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 15번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2021년 3월 모의고사 15번").set(data15)

        val data16 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 16번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 1
        )
        students.document("2021년 3월 모의고사 16번").set(data16)

        val data17 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 17번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 5
        )
        students.document("2021년 3월 모의고사 17번").set(data17)

        val data18 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 18번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 2
        )
        students.document("2021년 3월 모의고사 18번").set(data18)

        val data19 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 19번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2021년 3월 모의고사 19번").set(data19)

        val data20 = hashMapOf(
            "출제 년도" to "2021년 3월 모의고사",
            "경로" to "/3학년/사회탐구/생활과 윤리/2021년 3월 모의고사/2021년 3월 모의고사 20번.png",
            "시도 횟수" to 1,
            "정답수" to 1,
            "정답률" to 100,
            "정답" to 3
        )
        students.document("2021년 3월 모의고사 20번").set(data20)


        var st = ""

        val docRef = students.document("2021년 3월 모의고사 8번")

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
