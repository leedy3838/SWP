package com.example.androidstudioproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerviewpractice.*

class MainActivity : AppCompatActivity() {

    val DataList = arrayListOf(
        Data(R.drawable.selector_practice, "0번"),
        Data(R.drawable.selector_practice, "1번"),
        Data(R.drawable.selector_practice, "2번"),
        Data(R.drawable.selector_practice, "3번"),
        Data(R.drawable.selector_practice, "4번"),
        Data(R.drawable.selector_practice, "5번"),
        Data(R.drawable.selector_practice, "6번"),
        Data(R.drawable.selector_practice, "7번"),
        Data(R.drawable.selector_practice, "8번"),
        Data(R.drawable.selector_practice, "9번"),
        Data(R.drawable.selector_practice, "10번")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerviewpractice)


        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = CustomAdapter(DataList);
    }
}
