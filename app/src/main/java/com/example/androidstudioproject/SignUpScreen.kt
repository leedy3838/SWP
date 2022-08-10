package com.example.androidstudioproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.sign_up.*


class SignUpScreen : AppCompatActivity() {

    private var mPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)

    }

    fun duplicateCheckClicked(v: View){
        val db = FirebaseFirestore.getInstance()
        var idFlag = true

        db.collection("user")
            .get()
            .addOnSuccessListener{ col ->
                for(doc in col){
                    val name = doc.id
                    if(sign_up_id.getText().toString() == name) {
                        idFlag = false
                        break
                    }
                }

                if(idFlag == false) {
                    sign_up_success.isVisible = false
                    sign_up_fail.isVisible = true
                    sign_up_id_btn.isEnabled = false
                }else {
                    sign_up_fail.isVisible = false
                    sign_up_success.isVisible = true
                    sign_up_id_btn.isEnabled = true
                }
            }
    }

    fun idSaveClicked(v : View){
        mPref?.edit()?.run {
            putString("userName", sign_up_id.getText().toString())
            apply()
        }
        Toast.makeText(this, "ID 설정됨", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SettingPassword::class.java))
    }
}