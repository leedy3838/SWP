package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingFragment : PreferenceFragmentCompat() {

    lateinit var prefs : SharedPreferences



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        if(rootKey == null){
            prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        }

    }


}
