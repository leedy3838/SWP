package com.example.androidstudioproject

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.*


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
        val gradePre : Preference?= findPreference("userGradeSetting")
        val timePre : Preference? = findPreference("timeSettingPref")
        val subjectPre : Preference? = findPreference("subject")
        
        difPre?.setSummary(mPref?.getString("difficulty","난이도를 선택해주세요"))
        gradePre?.setSummary(mPref?.getString("userGradeSetting","학년을 선택해주세요"))
        timePre?.setSummary(mPref?.getInt("hour",0).toString()+"시간  "+mPref?.getInt("minute",0).toString()+"분")
        subjectPre?.setSummary(mPref?.getString("subject","과목을 선택해주세요"))
        checked()
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
            }
            else if (key.equals("subject")) {
                mPref.edit().run {
                    val listPreference : ListPreference?= findPreference("detailSubject")
                    val select = mPref.getString(key, "")
                    val grade = mPref.getString("userGradeSetting","")
                    if(select.equals("국어")){
                        if(grade.equals("3학년")) listPreference?.setEntries(R.array.grade3_korean)
                        else if(grade.equals("1학년")) listPreference?.setEntries(R.array.common)
                    }
                    preference?.setSummary(select)
                    apply()
                }
            }
            else if (key.equals("userGradeSetting")) {
                checked()
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            } else if (key.equals("hour")||key.equals("minute")) {
                mPref.edit().run {
                    val timePre : Preference? = findPreference("timeSettingPref")
                    timePre?.setSummary(
                        mPref.getInt("hour", 0).toString() + "시간  " + mPref.getInt("minute", 0)
                            .toString() + "분")
                    apply()
                }

            }
        }
    private fun checked(){
        val detailSubjectPre : Preference? = findPreference("detailSubject")
        val grade = mPref?.getString("userGradeSetting","")
        val subject = mPref?.getString("subject","")
        if(grade.equals("")||subject.equals("")){
            detailSubjectPre?.setEnabled(false)
        }else{
            detailSubjectPre?.setEnabled(true)
            val listPreference : ListPreference?= findPreference("detailSubject")
            //if(grade.equals("3학년"))
        }
    }
}