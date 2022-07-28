package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.retry_problem_select.*

class RetryProblemSelectScreen : AppCompatActivity() {
    lateinit var setintent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retry_problem_select)

        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val problem = intent.getStringExtra("문제 정보").toString()

        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("고등학생")
            .document(grade)
            .collection(subject)
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
                    .into(imageViewRetryProblem)

                setintent = Intent(this, ProblemSolveScreen::class.java)

                setintent.putExtra("정답률", document.get("정답률") as Long)
                setintent.putExtra("학년", grade)
                setintent.putExtra("과목", subject)
                setintent.putExtra("문제 정보", problem)
                setintent.putExtra("풀어본 문제",true)
            }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, RetryProblemScreen::class.java))
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun back(v : View){
        startActivity(Intent(this, RetryProblemScreen::class.java))
    }

    fun toProblemSolve(v : View){
        startActivity(setintent)
    }
}