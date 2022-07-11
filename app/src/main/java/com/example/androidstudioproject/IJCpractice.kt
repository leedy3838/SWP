package com.example.androidstudioproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.ijc_practice.*

class IJCPractice:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ijc_practice)

        btnIJC.setOnClickListener {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.getReference("image.png")
            Glide.with(btnIJC).load(storageRef).into(imageViewIJC)
        }
    }
}