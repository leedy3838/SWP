package com.example.androidstudioproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.problem_solve.*
import kotlinx.android.synthetic.main.retry_problem.*

class RetryProblemScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retry_problem)

        val DataList = mutableListOf<Data>()
        val db = FirebaseFirestore.getInstance()
        var st = ""

        val docRef = db.collection("다시 풀기")
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

                retryProblemList.layoutManager = LinearLayoutManager(this)
                retryProblemList.adapter = customAdapter

                //RecyclerView에 onClickListener 붙이기
                customAdapter.setItemClickListener(object: CustomAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        // 클릭 시 이벤트 작성
                        retryProblemSelectClicked(v)
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

    fun retryProblemSelectClicked(v : View){
        startActivity(Intent(this, RetryProblemSelectScreen::class.java))
    }
}
