package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.wrong_problem.*

class WrongProblemScreen : AppCompatActivity() {
    private val DataList = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wrong_problem)

        val db = FirebaseFirestore.getInstance()

        val user = intent.getStringExtra("user").toString()

        val docRef = db.collection("틀린 문제")
            .document(user)
            .collection(user)

        docRef
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.get("base").toString() == "yes")
                        continue

                    //name grade subject
                    DataList.add(
                        Data(
                            document.id,
                            document.get("문제 정보").toString(),
                            document.get("학년").toString(),
                            document.get("과목").toString()
                        )
                    )
                }

                val customAdapter = CustomAdapter(DataList)

                wrongProblemList.layoutManager = LinearLayoutManager(this)
                wrongProblemList.adapter = customAdapter

                //RecyclerView에 onClickListener 붙이기
                customAdapter.setItemClickListener(object: CustomAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int, DataList: List<Data>) {
                        val intent = Intent(v.context, WrongProblemNextScreen::class.java)

                        intent.putExtra("user", user)
                        intent.putExtra("학년", DataList[position].grade)
                        intent.putExtra("과목", DataList[position].subject)
                        intent.putExtra("문제 정보", DataList[position].info)

                        startActivity(intent)
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
}