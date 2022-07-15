package com.example.androidstudioproject

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingFragment : PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_with_preference_screen, rootKey)
    }
}
