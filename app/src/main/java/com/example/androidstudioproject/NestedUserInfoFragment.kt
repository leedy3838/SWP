package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class NestedUserInfoFragment : PreferenceFragmentCompat() {
    lateinit var prefs : SharedPreferences


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.nested_user_info_setting, rootKey)

        var gradePreference : ListPreference? = findPreference("userGradeSetting")

        gradePreference?.summaryProvider =
            ListPreference.SimpleSummaryProvider.getInstance()
    }
}