package com.example.androidstudioproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.androidstudioproject.databinding.SettingPasswordBinding
import com.google.firebase.firestore.FirebaseFirestore


class SettingPassword : AppCompatActivity() {
    lateinit var binding: SettingPasswordBinding

    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var getResultForQnA: ActivityResultLauncher<Intent>
    private var mPref: SharedPreferences? = null
    private var sharedPref : SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPref = PreferenceManager.getDefaultSharedPreferences(this)
        // 공용 공유프리퍼런스 활용

        sharedPref = getSharedPreferences("appLock", Context.MODE_PRIVATE)
        // 비밀번호용 공유 프리퍼런스

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("user")
        lateinit var user : String


        binding = SettingPasswordBinding.inflate(layoutInflater)
        // 첫 접속 구분하기 위한 SharedPreference 활용
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var first: Boolean = pref.getBoolean("isFirst", true)

        // 다른 액티비티를 실행 후 콜백 함수까지 실행하기 위한 registerForActivityResult 선언
        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        // 새로운 액티비티로 넘어감
        { result ->
            // 결과값을 받아옴
            println("check1")
            println(result.data?.getIntExtra("requestCode", 0))
            when (result.data?.getIntExtra("requestCode", 0)
            ) {
                // 첫 접속 시 초기화
                AppLockConst.INIT_PASSLOCK -> {
                    println("check1-1")
                    if (result.resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "암호 설정 됨", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, SettingGrade::class.java))
                        // 학년 설정 액티비티로 넘어감
                    }
                }

                // 비밀번호 분실 시 초기화
                AppLockConst.ENABLE_PASSLOCK -> {
                    println("check1-2")
                    if (result.resultCode == Activity.RESULT_OK) {
                        var password : String? = sharedPref?.getString("applock","")
                        user = mPref?.getString("userName","default").toString()
                        docRef
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    docRef.document(user).update("password", password)
                                }
                            }
                        Toast.makeText(this, "암호 초기화 됨", Toast.LENGTH_SHORT).show()
                    }
                }

                AppLockConst.CHANGE_PASSWORD -> {
                    println("check1-3")
                    if (result.resultCode == Activity.RESULT_OK) {
                        var password : String? = sharedPref?.getString("applock","")
                        user = mPref?.getString("userName","default").toString()
                        docRef
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    docRef.document(user).update("password", password)
                                }
                            }
                        Toast.makeText(this, "암호 변경 됨", Toast.LENGTH_SHORT).show()
                    }
                }

                AppLockConst.UNLOCK_PASSWORD -> {
                    println("check1-4")
                    if (result.resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "잠금 해제 됨", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 비밀번호 분실 시 초기화 버튼 클릭 후 질문/답 액티비티로 넘어가기 위한 것
        getResultForQnA = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        // 새로운 액티비티로 넘어감
        { result ->
            // 결과값을 받아옴
            println("check_pass??")
            println(result.data?.getStringExtra("QnAFlag"))
            when (result.data?.getStringExtra("QnAFlag"))
            {
                "noPass" -> {
                    println("check_noPass")
                    if (result.resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "암호 초기화 실패", Toast.LENGTH_SHORT).show()
                    }
                }
                // 질문/답 통과 시 실행
                "Pass" -> {
                    println("check_Pass")
                    if (result.resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "질문/답 통과", Toast.LENGTH_SHORT).show()

                        AppLock(this).removePassLock()

                        val intent = Intent(this, AppPassWordActivity::class.java).apply {
                            putExtra(AppLockConst.type, AppLockConst.ENABLE_PASSLOCK)
                        }
                        getResult.launch(intent)
                    }
                }
            }
        }




        // 첫 접속 시 초기화
        if (first == true) {
            println("first access")
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.INIT_PASSLOCK)
            }
            setContentView(binding.root)
            getResult.launch(intent)
        } else {
            println("not first access")
            // 첫 접속이 아니면 실행
            setContentView(binding.root)
            /*
            binding.root가 아닌 R.layout.activity_main으로 할 경우
            init()과 버튼 클릭이 정상적으로 작동되지 않음 (이유는 아직 모름)
            */
        }





        // 분실 시 초기화 버튼을 눌렀을때
        binding.pwInit.setOnClickListener {

            val intent = Intent(this, UnlockQnA::class.java)
            getResultForQnA.launch(intent)
            // 질문/답 확인 액티비티로 넘어감
        }


        // 비밀번호 변경버튼을 눌렀을때
        binding.pwChange.setOnClickListener {
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.CHANGE_PASSWORD)
            }
            getResult.launch(intent)
        }

        // 시간 모두 소모 후 긴급 탈출 버튼 눌렀을때
//        println("check2")
//        if(lock && AppLock(this).isPassLockSet()) {
//            val intent = Intent(this, AppPassWordActivity::class.java).apply {
//                putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD)
//            }
//            println("check2-1")
//            getResult.launch(intent)
//        }
    }

    override fun onBackPressed() {
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val first: Boolean = pref.getBoolean("isFirst", true)

        if(first)
            startActivity(Intent(this, SignUpScreen::class.java))
        else
            startActivity(Intent(this, SettingScreen::class.java))
    }

    // 액티비티가 onResume인 경우
    // onStart로 할 경우 AppPasswordActivity의 finish() 이후에
    // onStart가 실행되어 액티비티가 종료 되지않고 무한반복되는 현상 발생 이유는 아직 찾지 못함
    override fun onResume() {
        super.onResume()

    }

    // 액티비티가 onPause인경우
    override fun onPause() {
        super.onPause()
    }

}