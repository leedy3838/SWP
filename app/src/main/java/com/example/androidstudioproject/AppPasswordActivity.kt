package com.example.androidstudioproject


import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.androidstudioproject.databinding.ActivityAppLockPasswordBinding

class AppPassWordActivity : AppCompatActivity(){
    private var oldPwd =""
    private var changePwdUnlock = false
    private lateinit var binding : ActivityAppLockPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppLockPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonArray = arrayListOf<Button>(binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7 ,binding.btn8, binding.btn9,
            binding.btnClear, binding.btnErase)

        for (button in buttonArray){
            button.setOnClickListener(btnListener)
        }

    }

    // 버튼 클릭 했을때
    private val btnListener = View.OnClickListener { view ->
        var currentValue = -1
        when(view.id){
            R.id.btn0 -> currentValue = 0
            R.id.btn1 -> currentValue = 1
            R.id.btn2 -> currentValue = 2
            R.id.btn3 -> currentValue = 3
            R.id.btn4 -> currentValue = 4
            R.id.btn5 -> currentValue = 5
            R.id.btn6 -> currentValue = 6
            R.id.btn7 -> currentValue = 7
            R.id.btn8 -> currentValue = 8
            R.id.btn9 -> currentValue = 9
            R.id.btnClear -> onClear()
            R.id.btnErase -> onDeleteKey()
        }

        val strCurrentValue = currentValue.toString() // 현재 입력된 번호 String으로 변경
        if (currentValue != -1){
            when {
                binding.etPasscode1.isFocused -> {
                    setEditText(binding.etPasscode1, binding.etPasscode2, strCurrentValue)
                }
                binding.etPasscode2.isFocused -> {
                    setEditText(binding.etPasscode2, binding.etPasscode3, strCurrentValue)
                }
                binding.etPasscode3.isFocused -> {
                    setEditText(binding.etPasscode3, binding.etPasscode4, strCurrentValue)
                }
                binding.etPasscode4.isFocused -> {
                    binding.etPasscode4.setText(strCurrentValue)
                }
            }
        }

        // 비밀번호를 4자리 모두 입력시
        if (binding.etPasscode4.text.isNotEmpty() && binding.etPasscode3.text.isNotEmpty()
            && binding.etPasscode2.text.isNotEmpty() && binding.etPasscode1.text.isNotEmpty()) {
            inputType(intent.getIntExtra("type", 0))
        }
    }

    // 한 칸 지우기를 눌렀을때
    private fun onDeleteKey() {

        when {
            binding.etPasscode1.isFocused -> {
                binding.etPasscode1.setText("")
            }
            binding.etPasscode2.isFocused -> {
                binding.etPasscode1.setText("")
                binding.etPasscode1.requestFocus()
            }
            binding.etPasscode3.isFocused -> {
                binding.etPasscode2.setText("")
                binding.etPasscode2.requestFocus()
            }
            binding.etPasscode4.isFocused -> {
                binding.etPasscode3.setText("")
                binding.etPasscode3.requestFocus()
            }
        }
    }

    // 모두 지우기
    private fun onClear(){

        binding.etPasscode1.setText("")
        binding.etPasscode2.setText("")
        binding.etPasscode3.setText("")
        binding.etPasscode4.setText("")
        binding.etPasscode1.requestFocus()
    }

    // 입력된 비밀번호를 합치기
    private fun inputedPassword():String {

        return "${binding.etPasscode1.text}${binding.etPasscode2.text}${binding.etPasscode3.text}${binding.etPasscode4.text}"
    }

    // EditText 설정
    private fun setEditText(currentEditText : EditText, nextEditText: EditText, strCurrentValue : String){
        currentEditText.setText(strCurrentValue)
        nextEditText.requestFocus()
        nextEditText.setText("")
    }

    // Intent Type 분류
    private fun inputType(type : Int){
        val intent = intent
        println("check3")
        when(type) {
            AppLockConst.INIT_PASSLOCK -> { // 잠금설정
                println("check3-1")
                if (oldPwd.isEmpty()) {
                    oldPwd = inputedPassword()
                    onClear()
                    binding.etInputInfo.text = "다시 한번 입력"
                } else {
                    if (oldPwd == inputedPassword()) {
                        AppLock(this).setPassLock(inputedPassword())
                        intent.putExtra("requestCode", AppLockConst.INIT_PASSLOCK)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else {
                        onClear()
                        oldPwd = ""
                        binding.etInputInfo.text = "비밀번호 입력"

                        val toast = Toast.makeText(this, "처음 입력하신 비밀번호와 다릅니다.\n다시 비밀번호를 입력해주십시오.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.BOTTOM, 0, 200)
                        toast.show()
                    }
                }
            }

            AppLockConst.ENABLE_PASSLOCK -> { // 분실 시 초기화
                println("check3-2")
                if (oldPwd.isEmpty()) {
                    oldPwd = inputedPassword()
                    onClear()
                    binding.etInputInfo.text = "다시 한번 입력"
                } else {
                    if (oldPwd == inputedPassword()) {
                        println("check3-2-1")
                        AppLock(this).setPassLock(inputedPassword())
                        intent.putExtra("requestCode", AppLockConst.ENABLE_PASSLOCK)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else {
                        onClear()
                        oldPwd = ""
                        binding.etInputInfo.text = "비밀번호 입력"
                    }
                }
            }

            AppLockConst.UNLOCK_PASSWORD -> {

                if (AppLock(this).checkPassLock(inputedPassword())) {
                    intent.putExtra("requestCode", AppLockConst.UNLOCK_PASSWORD)
                    setResult(Activity.RESULT_OK, intent)
                    println("check3-3")
                    finish()
                } else {
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    onClear()
                }
            }

            AppLockConst.CHANGE_PASSWORD -> { // 비밀번호 변경
                println("check3-4")
                if (AppLock(this).checkPassLock(inputedPassword()) && !changePwdUnlock) {
                    onClear()
                    changePwdUnlock = true
                    binding.etInputInfo.text = "새로운 비밀번호 입력"
                } else if (changePwdUnlock) {
                    if (oldPwd.isEmpty()) {
                        oldPwd = inputedPassword()
                        onClear()
                        binding.etInputInfo.text = "새로운 비밀번호 다시 입력"
                    } else {
                        if (oldPwd == inputedPassword()) {
                            AppLock(this).setPassLock(inputedPassword())
                            intent.putExtra("requestCode", AppLockConst.CHANGE_PASSWORD)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else {
                            onClear()
                            oldPwd = ""
                            binding.etInputInfo.text = "현재 비밀번호 다시 입력"
                            changePwdUnlock = false
                        }
                    }
                } else {
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    changePwdUnlock = false
                    onClear()
                }
            }
        }
    }

    override fun onBackPressed() {
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().run {
            putBoolean("isFirst",true)
        }.apply()

        startActivity(Intent(this, SignUpScreen::class.java))
    }
}