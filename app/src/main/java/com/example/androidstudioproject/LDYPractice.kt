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

        var str : String = "image/새로운 이미지.png"
        var db = FirebaseFirestore.getInstance()
        var st : String = ""

        val docRef = db.collection("korean").document("jakmoon")
        docRef.get()
            .addOnSuccessListener { document ->
                    st = document.get("image2").toString()
            }

        btnLDY.setOnClickListener {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.getReference(st)
            Glide.with(btnLDY).load(storageRef).into(imageViewLDY)
        }
    }
}
