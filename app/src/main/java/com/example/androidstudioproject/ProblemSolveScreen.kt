package com.example.androidstudioproject

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
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
        val btn_giveUp = findViewById<Button>(R.id.giveUp)
        //정답 제출 기능
        val btn_answerSubmit = findViewById<Button>(R.id.answerSubmit)
        //입력한 숫자
        val textInputEditText = findViewById<EditText>(R.id.textInputEditText)

        btn_answerSubmit.isEnabled = false  // 버튼 초기 상태 비활성화

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트 입력 후
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                val message = textInputEditText.getText().toString();
                btn_answerSubmit.isEnabled = !message.isEmpty()  // 정답 입력 칸에 숫자가 생기면 버튼 활성화
            }
        })


        btn_giveUp.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문제 포기")
            builder.setMessage("문제를 포기하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
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
        var answerRate : Int = intent.getIntExtra("정답률", 100)
        val user = intent.getStringExtra("유저")

        var answer : Long = 0    // 정답 번호를 받아오기 전 초기화
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
                var find = false

                while(!find) {
                    for (document in result) {
                        answerRateInDocument = document.get("정답률") as Long
                        answer = document.get("정답") as Long
                        questionYear = document.get("출제 년도").toString()
                        tryNum = document.get("시도 횟수") as Long
                        answerNum = document.get("정답수") as Long

                        if (answerRateInDocument <= answerRate) {
                            st = document.get("경로").toString()
                            problemInfo.text = questionYear

                            intent.putExtra("정답률", answerRateInDocument)
                            intent.putExtra("학년", grade)
                            intent.putExtra("과목", subject)
                            intent.putExtra("유저", user)

                            println(answer)

                            find = true
                            break
                        }
                    }

                    answerRate += 5
                    println(answerRate)
                }

                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef: StorageReference = storage.getReference(st)

                Glide.with(this)
                    .load(storageRef)
                    //대기 화면(placeholder)
                    .placeholder(R.drawable.text_background)
                    .into(imageViewProblemSolve)

                btn_answerSubmit.setOnClickListener {     // 정답 제출 버튼 클릭 시
                    val input = textInputEditText.text.toString()
                    val input1 : Long = input.toLong()
                    val builder = AlertDialog.Builder(this)

                    builder.setTitle("정답을 제출하시겠습니까?")
                    builder.setMessage("한번 제출한 답은 변경할 수 없습니다.")
                        .setPositiveButton("제출", DialogInterface.OnClickListener { dialog, which ->
                            val builder = AlertDialog.Builder(this)

                            if (answer == input1) {

                                builder.setMessage("정답입니다!")
                                    .setPositiveButton(
                                        "확인",
                                        DialogInterface.OnClickListener { dialog, which ->
                                            startActivity(Intent(this, ProblemSolveNextScreen::class.java))
                                        })
                            }
                            else {
                                builder.setMessage("오답입니다.")
                                    .setPositiveButton(
                                        "확인",
                                        DialogInterface.OnClickListener { dialog, which ->
                                            startActivity(Intent(this, ProblemSolveNextScreen::class.java))
                                        })
                            }

                            val alertDialog = builder.create()
                            val window = alertDialog.window
                            window?.setGravity(Gravity.CENTER)
                            alertDialog.show()
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
        startActivity(intent)
    }
}