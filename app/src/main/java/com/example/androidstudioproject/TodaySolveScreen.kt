package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.today_solve.*

class TodaySolveScreen : AppCompatActivity() {

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

    val customAdapter = CustomAdapter(DataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_solve)

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

    fun home(v : View){
        startActivity(Intent(this, BasicScreen::class.java))
    }

    fun toTodaySolvedNext(v : View){
        startActivity(Intent(this, TodaySolvedNextScreen::class.java))
    }
}