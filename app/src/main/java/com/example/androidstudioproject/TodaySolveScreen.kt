package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.retry_problem.*
import kotlinx.android.synthetic.main.today_solve.*

class TodaySolveScreen : AppCompatActivity() {


    override fun onBackPressed() {
        startActivity(Intent(this, BasicScreen::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_solve)


        val DataList = mutableListOf<Data>()
        val db = FirebaseFirestore.getInstance()
        var st = ""

        val docRef = db.collection("오늘 푼 문제")
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

                todaySolveList.layoutManager = LinearLayoutManager(this)
                todaySolveList.adapter = customAdapter

                //RecyclerView에 onClickListener 붙이기
                customAdapter.setItemClickListener(object: CustomAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        // 클릭 시 이벤트 작성
                        toTodaySolvedNext(v)
                    }
                })
            }
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toTodaySolvedNext(v : View){
        startActivity(Intent(this, TodaySolvedNextScreen::class.java))
    }
}