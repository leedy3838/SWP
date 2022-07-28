package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


class SettingFragment : PreferenceFragmentCompat() {

    lateinit var prefs : SharedPreferences
    private var mPref: SharedPreferences? = null






    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
        mPref = PreferenceManager.getDefaultSharedPreferences(context)
        mPref?.registerOnSharedPreferenceChangeListener(mPrefChangeListener)
        if(rootKey == null){
            prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        }
        val difPre : Preference?= findPreference("difficulty")
        val gradePre : Preference?= findPreference("grade")
        val timePre : Preference? = findPreference("timeSettingPref")
        
        difPre?.setSummary(mPref?.getString("difficulty","난이도를 선택해주세요"))
        gradePre?.setSummary(mPref?.getString("grade","난이도를 선택해주세요"))
        timePre?.setSummary(mPref?.getInt("hour",0).toString()+"시간  "+mPref?.getInt("minute",0).toString()+"분")



    }

    private val mPrefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { mPref, key ->
            val preference: Preference? = findPreference(key)
            println(key)
            if (key.equals("difficulty")) {
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            } else if (key.equals("subject")) {
                Log.d("TAG", key + "SELECTED")
            } else if (key.equals("grade")) {
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            } else if (key.equals("hour")||key.equals("minute")) {
                mPref.edit().run {
                    preference?.setSummary(
                        mPref.getInt("hour", 0).toString() + "시간  " + mPref.getInt("minute", 0)
                            .toString() + "분")
                    apply()
                }

            }
        }
}