package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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



    }

    private val mPrefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { mPref, key ->
            val preference: Preference? = findPreference(key)
            if (key.equals("difficulty")) {
                mPref.edit().run() {
                    preference?.setSummary(mPref.getString(key,""))
                    apply()
                }
            } else if (key.equals("subject")) {
                Log.d("TAG", key + "SELECTED")
            } else if (key.equals("upperWidget")) {
                mPref.edit().run() {
                    preference?.setSummary(mPref.getString(key,""))
                    apply()
                }
            } else if (key.equals("grade")) {
                mPref.edit().run() {
                    preference?.setSummary(mPref.getString(key,""))
                    apply()
                }
            }
        }
}