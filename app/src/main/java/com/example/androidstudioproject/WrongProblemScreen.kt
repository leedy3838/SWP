package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.wrong_problem.*

class WrongProblemScreen : AppCompatActivity() {

    val DataList = arrayListOf(
        Data(R.drawable.text_background, "1번"),
        Data(R.drawable.text_background, "2번"),
        Data(R.drawable.text_background, "3번"),
        Data(R.drawable.text_background, "4번"),
        Data(R.drawable.text_background, "5번"),
        Data(R.drawable.text_background, "6번"),
        Data(R.drawable.text_background, "7번"),
        Data(R.drawable.text_background, "8번"),
        Data(R.drawable.text_background, "9번"),
        Data(R.drawable.text_background, "10번")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wrong_problem)

        wrongProblemList.layoutManager = LinearLayoutManager(this)
        wrongProblemList.adapter = CustomAdapter(DataList)
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }
}