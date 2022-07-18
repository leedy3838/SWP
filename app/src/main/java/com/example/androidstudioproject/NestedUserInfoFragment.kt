
package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class NestedUserInfoFragment : PreferenceFragmentCompat() {
    lateinit var prefs : SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.nested_user_info_setting, rootKey)

    }
}
