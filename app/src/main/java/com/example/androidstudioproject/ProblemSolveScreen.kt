package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.problem_solve.*
import kotlin.math.roundToLong


class ProblemSolveScreen :AppCompatActivity() {
    private lateinit var sendintent : Intent
    private lateinit var problemId :String

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
                val message = textInputEditText.text.toString()
                btn_answerSubmit.isEnabled = message.isNotEmpty()  // 정답 입력 칸에 숫자가 생기면 버튼 활성화
            }
        })


        btn_giveUp.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문제 포기")
            builder.setMessage("문제를 포기하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    startActivity(Intent(this, BasicScreen::class.java))
                }
                .setNegativeButton("취소") { _, _ ->
                    // 취소 버튼 클릭
                }
            val alertDialog = builder.create()
            val window = alertDialog.window
            window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }



        val user = intent.getStringExtra("user").toString()
        val grade = intent.getStringExtra("학년").toString()
        val subject = intent.getStringExtra("과목").toString()
        val problem = intent.getStringExtra("문제 정보").toString()
        var answerRate : Long = intent.getLongExtra("정답률", 100)

        val selectSubject = intent.getStringExtra("세부과목").toString()

        val db = FirebaseFirestore.getInstance()
        var st = ""

        var docName = ""

        val DataList = mutableListOf<Data>()

        db.collection("오늘 푼 문제")
            .document(user)
            .collection(user)
            .get()
            .addOnSuccessListener{ col ->
                for(doc in col){
                    val age = doc.get("학년").toString()
                    val selectedSub = doc.get("세부과목").toString()
                    val sub = doc.get("과목").toString()
                    val problemInfo = doc.get("문제 정보").toString()

                    DataList.add(Data("", problemInfo, age, sub, selectedSub))
                }
            }

        var answer : Long = 0    // 정답 번호를 받아오기 전 초기화
        var questionYear : String
        var tryNum : Long = 0
        var answerRateInDocument : Long
        var answerNum : Long = 0

        var docRef = db.collection("고등학생")
            .document(grade)
            .collection(subject)


        if (selectSubject != "없음") {
            docRef = db.collection("고등학생")
                .document(grade)
                .collection(subject)
                .document(selectSubject)
                .collection(selectSubject)
        }
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
                        docName = document.id

                        //basicScreen 이외에서 넘어온 경우
                        if(problem == document.id){
                            st = document.get("경로").toString()
                            problemInfo.text = questionYear

                            if(intent.getStringExtra("이전 화면") == "다시 풀기")
                                sendintent = Intent(this, RetryProblemScreen::class.java)
                            if(intent.getStringExtra("이전 화면") == "틀린 문제")
                                sendintent = Intent(this, WrongProblemScreen::class.java)
                            if(intent.getStringExtra("이전 화면") == "오늘 푼 문제")
                                sendintent = Intent(this, TodaySolveScreen::class.java)

                            sendintent.putExtra("user", user)

                            find = true
                            break
                        }

                        //basicScreen에서 넘어온 경우
                        if (answerRate >= answerRateInDocument && answerRate <= answerRateInDocument+5 && problem == "없음") {
                            var existInToday = false

                            for(i in DataList){
                                val age = i.grade
                                val selectedSub = i.detailSubject
                                val sub = i.subject
                                val problemInfo = i.info

                                println(age +" " + grade)
                                println(selectedSub +" " + selectSubject)
                                println(sub +" " + subject)
                                println(problemInfo +" " + document.id)

                                if(age == grade && selectedSub == selectSubject && sub == subject && problemInfo == document.id){
                                    existInToday = true
                                    break
                                }
                            }

                            println(existInToday)
                            if(existInToday)
                                continue

                            st = document.get("경로").toString()
                            problemInfo.text = questionYear

                            sendintent = Intent(this, ProblemSolveNextScreen::class.java)
                            sendintent.putExtra("정답률", answerRateInDocument)
                            sendintent.putExtra("학년", grade)
                            sendintent.putExtra("과목", subject)
                            sendintent.putExtra("세부과목", selectSubject)
                            sendintent.putExtra("문제 정보", document.id)
                            sendintent.putExtra("user", user)

                            problemId = document.id

                            println(answerRate)
                            println(answerRateInDocument)

                            find = true
                            break
                        }
                    }

                    println(answerRate)
                    answerRate += 5
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
                    var answerRateUpdate : Long
                    builder.setTitle("정답을 제출하시겠습니까?")
                    builder.setMessage("한번 제출한 답은 변경할 수 없습니다.")
                        .setPositiveButton("제출") { _, _ ->

                            val builder = AlertDialog.Builder(this)

                            if (answer == input1) {
                                //틀린 문제에 있는 문제를 맞추면 틀린 문제 리스트에서 제거
                                if (intent.getStringExtra("이전 화면") == "틀린 문제") {
                                    val name = intent.getStringExtra("이름").toString()
                                    db.collection("틀린 문제").document(user).collection(user)
                                        .document(name).delete()
                                }
                                if (problem == "없음") {
                                    docRef.document(docName)
                                        .update("시도 횟수", ++tryNum)  // 정답일 때 시도횟수 1 증가
                                    docRef.document(docName)
                                        .update("정답수", ++answerNum)  // 정답일 때 정답수 1 증가

                                    answerRateUpdate =
                                        ((answerNum.toDouble() / tryNum.toDouble()) * 100.0).roundToLong()  // 정답률 저장 변수
                                    docRef.document(docName).update("정답률", answerRateUpdate)
                                    println(answerRateUpdate)

                                    //문제 풀기에서 맞았을 때 오늘 푼 문제에 추가

                                    val name: String = if (selectSubject == "없음")
                                        "$grade $subject $problemId"
                                    else
                                        "$grade $subject $selectSubject $problemId"

                                    val retryRef =
                                        db.collection("오늘 푼 문제").document(user).collection(user)

                                    val data = hashMapOf(
                                        "학년" to grade,
                                        "과목" to subject,
                                        "문제 정보" to problemId,
                                        "세부과목" to selectSubject,
                                        "푼 날짜" to FieldValue.serverTimestamp()
                                    )
                                    retryRef.document(name).set(data)
                                }

                                builder.setMessage("정답입니다!")
                                    .setCancelable(false)    // 뒤로가기 불가
                                    .setPositiveButton(
                                        "확인"
                                    ) { _, _ -> startActivity(sendintent) }
                            } else {
                                //기본 화면에서 문제 풀기로 바로 넘어왔을 때 (오답) 정답률 수정
                                if (problem == "없음") {
                                    docRef.document(docName)
                                        .update("시도 횟수", ++tryNum)  // 오답일 때 시도횟수 1 증가

                                    answerRateUpdate =
                                        ((answerNum.toDouble() / tryNum.toDouble()) * 100.0).roundToLong()  // 정답률 저장 변수
                                    docRef.document(docName).update("정답률", answerRateUpdate)

                                    //문제 풀기에서 틀렸을 때 틀린 문제에 추가

                                    val name: String = if (selectSubject == "없음")
                                        "$grade $subject $problemId"
                                    else
                                        "$grade $subject $selectSubject $problemId"

                                    val retryRef =
                                        db.collection("틀린 문제").document(user).collection(user)

                                    val data = hashMapOf(
                                        "학년" to grade,
                                        "과목" to subject,
                                        "문제 정보" to problemId,
                                        "세부과목" to selectSubject
                                    )
                                    retryRef.document(name).set(data)
                                }

                                builder.setMessage("오답입니다. 다시 시도해 보세요.")
                                    .setPositiveButton(
                                        "확인"
                                    )
                                    { _, _ -> }
                            }

                            val alertDialog = builder.create()
                            val window = alertDialog.window
                            window?.setGravity(Gravity.CENTER)
                            alertDialog.show()
                        }
                        .setNegativeButton("취소") { _, _ ->
                            //취소 버튼 클릭
                        }
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
                .setPositiveButton("포기") { _, _ ->
                    startActivity(Intent(this, BasicScreen::class.java))
                }
                .setNegativeButton("취소") { _, _ ->
                    //취소 버튼 클릭
                }
            val alertDialog = builder.create()
            val window = alertDialog.window
            window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }
}