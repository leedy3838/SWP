package com.example.androidstudioproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.widget.EditText
import androidx.preference.*


class SettingFragment : PreferenceFragmentCompat(){

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
        val userNamePre : EditTextPreference? = findPreference("userName")
        
        difPre?.setSummary(mPref?.getString("difficulty","난이도를 선택해주세요"))
        gradePre?.setSummary(mPref?.getString("userGradeSetting","학년을 선택해주세요"))
        timePre?.setSummary(mPref?.getInt("hour",0).toString()+"시간  "+mPref?.getInt("minute",0).toString()+"분")
        subjectPre?.setSummary(mPref?.getString("subject","과목을 선택해주세요"))
        userNamePre?.setSummary(mPref?.getString("userName","닉네임을 선택해주세요"))
        checked()

        userNamePre?.setOnBindEditTextListener(object : EditTextPreference.OnBindEditTextListener {
            override fun onBindEditText(editText: EditText) {
                editText.inputType = InputType.TYPE_CLASS_TEXT
                editText.selectAll() // select all text
                val maxLength = 10
                editText.filters =
                    arrayOf<InputFilter>(LengthFilter(maxLength))
            }
        })
    }


    private val mPrefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { mPref, key ->
            val preference: Preference? = findPreference(key)
            if (key.equals("difficulty")) {
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            }
            else if (key.equals("subject")) {
                mPref.edit().run {
                    remove("detailSubject")
                    checked()
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            }
            else if (key.equals("userGradeSetting")) {
                mPref.edit().run {
                    remove("detailSubject")
                    checked()
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
            } else if(key.equals("detailSubject")){
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key, ""))
                    apply()
                }
            } else if(key.equals("userName")){
                mPref.edit().run {
                    preference?.setSummary(mPref.getString(key,""))
                    apply()
                }
            }
        }




    private fun checked(){
        val detailSubjectPre : Preference? = findPreference("detailSubject")
        val grade = mPref?.getString("userGradeSetting","")
        val subject = mPref?.getString("subject","")
        var arr : Int = R.array.common
        if(grade.equals("")||subject.equals("")){
            detailSubjectPre?.setEnabled(false)
            detailSubjectPre?.setSummary("학년, 과목을 선택해주세요")
        }else{
            detailSubjectPre?.setEnabled(true)
            val listPreference : ListPreference?= findPreference("detailSubject")
            if(grade.equals("1학년")){
                detailSubjectPre?.setEnabled(false)
                detailSubjectPre?.setSummary("선택과목 없음")
                mPref?.edit()?.run {
                    putString("detailSubject","없음")
                }
            }
            else if(grade.equals("2학년")){
                if(subject.equals("과학탐구")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr = R.array.grade2_science
                }
                else if(subject.equals("사회탐구")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr = R.array.grade23_social
                }
                else{
                    detailSubjectPre?.setEnabled(false)
                    detailSubjectPre?.setSummary("선택과목 없음")
                    mPref?.edit()?.run {
                        putString("detailSubject","없음")
                    }
                }
            }
            else if(grade.equals("3학년")){
                if(subject.equals("과학탐구")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr= R.array.grade3_science
                }
                else if(subject.equals("사회탐구")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr=R.array.grade23_social
                }
                else if(subject.equals("국어")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr =R.array.grade3_korean
                }
                else if(subject.equals("수학")){
                    detailSubjectPre?.setEnabled(true)
                    detailSubjectPre?.setSummary(mPref?.getString("detailSubject",""))
                    arr=R.array.grade3_math
                }
                else{
                    detailSubjectPre?.setEnabled(false)
                    detailSubjectPre?.setSummary("선택과목 없음")
                    mPref?.edit()?.run {
                        putString("detailSubject","없음")
                    }
                }
            }else{
                detailSubjectPre?.setEnabled(false)
                return
            }

            listPreference?.setEntries(arr)
            listPreference?.setEntryValues(arr)
        }

    }


}