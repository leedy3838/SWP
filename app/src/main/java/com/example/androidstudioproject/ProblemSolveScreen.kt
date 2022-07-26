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

        //뒤로 가기 기능
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

        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val answerRate : Int = intent.getIntExtra("정답률", 100)

        var answer : Long
        var questionYear : String
        var tryNum : Long
        var answerRateInDocument : Long
        var answerNum : Long

        intent = Intent(this, ProblemSolveNextScreen::class.java)

        val docRef = db.collection("고등학생")
            .document(grade)
            .collection(subject)

        docRef
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    answerRateInDocument = document.get("정답률") as Long

                    answer = document.get("정답") as Long
                    questionYear = document.get("출제 년도").toString()
                    tryNum = document.get("시도 횟수") as Long
                    answerNum = document.get("정답수") as Long

                    if(answerRateInDocument <= answerRate) {
                        st = document.get("경로").toString()
                        problemInfo.text = questionYear

                        intent.putExtra("정답률", answerRateInDocument)
                        intent.putExtra("학년", grade)
                        intent.putExtra("과목", subject)

                        break
                    }
                }

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
        startActivity(intent)
    }

    fun toProblemSolveNext(v : View){
        startActivity(intent)
    }


}