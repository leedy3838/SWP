package com.example.androidstudioproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.ldy_practice.*

class LDYPractice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ldy_practice)

        var str = "/1학년/과학탐구/newimage.png"
        var db = FirebaseFirestore.getInstance()
        var st : String = ""

        val docRef = db.collection("고등학생")
            .document("3학년")
            .collection("수학")
            .document("공통")
            .collection("공통")
            .document("1번 문제")

        docRef.get()
            .addOnSuccessListener { document ->
                    st = document.get("문제 이미지").toString()
            }

        btnLDY.setOnClickListener {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.getReference(st)

            Glide.with(btnLDY).load(storageRef).into(imageViewLDY)
        }
    }
}
