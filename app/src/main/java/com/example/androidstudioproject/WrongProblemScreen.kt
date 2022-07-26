package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.wrong_problem.*

class WrongProblemScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wrong_problem)

        val DataList = mutableListOf<Data>()
        val db = FirebaseFirestore.getInstance()
        var st = ""

        val docRef = db.collection("틀린 문제")
            .document("LDY")
            .collection("LDY")

        docRef
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.get("경로").toString() == "null")
                        continue

                    DataList.add(Data(document.get("경로").toString()))
                }

                val customAdapter = CustomAdapter(DataList)

                wrongProblemList.layoutManager = LinearLayoutManager(this)
                wrongProblemList.adapter = customAdapter

                //RecyclerView에 onClickListener 붙이기
                customAdapter.setItemClickListener(object: CustomAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        // 클릭 시 이벤트 작성
                        toWrongProblemNext(v)
                    }
                })
            }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toWrongProblemNext(v : View){
        startActivity(Intent(this, WrongProblemNextScreen::class.java))
    }
}