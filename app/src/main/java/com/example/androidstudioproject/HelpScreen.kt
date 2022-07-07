package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.help_screen.*
import kotlinx.android.synthetic.main.retry_problem.*

class HelpScreen :AppCompatActivity(){

    val DataList = arrayListOf(
        Data(R.drawable.dot, "도움말 1"),
        Data(R.drawable.dot, "도움말 2"),
        Data(R.drawable.dot, "도움말 3"),
        Data(R.drawable.dot, "도움말 4"),
        Data(R.drawable.dot, "도움말 5"),
        Data(R.drawable.dot, "도움말 6"),
        Data(R.drawable.dot, "도움말 7"),
        Data(R.drawable.dot, "도움말 8"),
        Data(R.drawable.dot, "도움말 9"),
        Data(R.drawable.dot, "도움말 10"),
        Data(R.drawable.dot, "도움말 11"),
        Data(R.drawable.dot, "도움말 12"),
        Data(R.drawable.dot, "도움말 13"),
        Data(R.drawable.dot, "도움말 14"),
        Data(R.drawable.dot, "도움말 15")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_screen)

        helpList.layoutManager = LinearLayoutManager(this)
        helpList.adapter = CustomAdapter(DataList)
    }

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }
}