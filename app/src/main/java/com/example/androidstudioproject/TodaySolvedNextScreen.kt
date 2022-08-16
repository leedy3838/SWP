package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.today_solved_next.*

class TodaySolvedNextScreen :AppCompatActivity() {
    private lateinit var setintent : Intent
    private lateinit var backintent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_solved_next)

        val user = intent.getStringExtra("user").toString()
        val grade = intent.getStringExtra("학년").toString()
        val detailSubject = intent.getStringExtra("세부과목").toString()
        val subject = intent.getStringExtra("과목").toString()
        val problem = intent.getStringExtra("문제 정보").toString()

        val db = FirebaseFirestore.getInstance()

        val docRef : DocumentReference

        if(detailSubject == "없음")
            docRef = db.collection("고등학생")
                .document(grade)
                .collection(subject)
                .document(problem)
        else
            docRef = db.collection("고등학생")
                .document(grade)
                .collection(subject)
                .document(detailSubject)
                .collection(detailSubject)
                .document(problem)

        docRef
            .get()
            .addOnSuccessListener { document ->
                val st = document.get("경로").toString()

                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef: StorageReference = storage.getReference(st)

                Glide.with(this)
                    .load(storageRef)
                    //대기 화면(placeholder)
                    .placeholder(R.drawable.text_background)
                    .into(imageViewTodaySolved)

                setintent = Intent(this, ProblemSolveScreen::class.java)

                setintent.putExtra("user", user)
                setintent.putExtra("학년", grade)
                setintent.putExtra("과목", subject)
                setintent.putExtra("세부과목",detailSubject)
                setintent.putExtra("문제 정보", problem)
                setintent.putExtra("이전 화면", "오늘 푼 문제")
            }

        backintent = Intent(this, TodaySolveScreen::class.java)
        backintent.putExtra("user", user)
    }

    override fun onBackPressed() {
        startActivity(backintent)
    }

    fun back(v : View){
        startActivity(backintent)
    }

    fun toProblemSolve(v : View){
        startActivity(setintent)
    }

    fun addToRetry(v : View){
        val db = FirebaseFirestore.getInstance()

        val name = intent.getStringExtra("이름").toString()
        val user = intent.getStringExtra("user").toString()
        val problem = intent.getStringExtra("문제 정보").toString()
        val detailSubject = intent.getStringExtra("세부과목").toString()
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()

        val docRef = db.collection("다시 풀기")
            .document(user)
            .collection(user)

        docRef
            .get()
            .addOnSuccessListener { result ->
                var flag = false

                for (document in result) {
                    if (document.id == name) {
                        flag = true
                        Toast.makeText(this, "이미 존재하는 문제입니다.", Toast.LENGTH_SHORT).show()
                        break
                    }
                }

                if (!flag) {
                    Toast.makeText(this, "다시 풀어보고 싶은 문제에 추가하였습니다.", Toast.LENGTH_SHORT).show()
                    val retryRef = db.collection("다시 풀기").document(user).collection(user)

                    val data = hashMapOf(
                        "학년" to grade,
                        "과목" to subject,
                        "문제 정보" to problem,
                        "세부과목" to detailSubject
                    )
                    retryRef.document(name).set(data)

                }
            }
    }

}