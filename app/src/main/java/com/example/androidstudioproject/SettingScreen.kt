package com.example.androidstudioproject

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class SettingScreen : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private var mPref: SharedPreferences? = null
    private val mPrefChangeListener = OnSharedPreferenceChangeListener{ mPref, key ->
        println("시작합니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        if (key.equals("difficultySetting")) {
            mPref.edit().run(){
                putString("difficulty","")
                println("난이도는!!!!!!!!!!!!!!!!!!"+mPref.getString("difficulty","-1"))
                apply()
            }
        } else if (key.equals("subject")) {
            Log.d("TAG", key + "SELECTED")
        } else if (key.equals("upperWidget")) {
            mPref.edit().run(){
                putBoolean("upperWidget",false)
                apply()
            }
        } else if (key.equals("userGradeSetting")) {
            mPref.edit().run(){
                putInt("grade",-1)
                apply()
            }
        }
        println("시간은::::::::::::::::"+mPref.getInt("hour",-1))
        println("난이도는:::::::::::::::"+mPref.getString("difficulty","-2"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.setting_screen)

        mPref = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        mPref?.registerOnSharedPreferenceChangeListener(mPrefChangeListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingFragment(), "setting_fragment")
            .commit()
    }

    override fun onResume() {
        super.onResume()
        mPref?.registerOnSharedPreferenceChangeListener(mPrefChangeListener)
    }
    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            pref.fragment
        )

        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "nested_fragment")
            .addToBackStack(null)
            .commit()
        return true
    }

}






