package com.example.androidstudioproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.problem_solve.*

class BasicScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_screen)
    }

    fun retryProblemClicked(v : View){
        startActivity(Intent(this, RetryProblemScreen::class.java))
    }

    fun settingClicked(v : View){
        startActivity(Intent(this, SettingScreen::class.java))
    }

    fun problemSolveClicked(v : View){
        intent = Intent(this, ProblemSolveScreen::class.java)

        startActivity(intent)
    }

    fun todaySolveClicked(v : View){
        startActivity(Intent(this, TodaySolveScreen::class.java))
    }

    fun wrongProblemClicked(v : View){
        startActivity(Intent(this, WrongProblemScreen::class.java))
    }
}