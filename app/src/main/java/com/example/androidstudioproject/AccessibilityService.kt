package com.example.androidstudioproject

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent


/**
 * Created by JW on 16. 10. 3..
 */
class AccessibilityService : AccessibilityService() {
    // 이벤트가 발생할때마다 실행되는 부분
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println("이벤트 출력!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+event.packageName)
//        Log.e(TAG, "Catch Event : " + event.toString());
        Log.e(TAG, "Catch Event Package Name : " + event.packageName)
        Log.e(TAG, "Catch Event TEXT : " + event.text)
        Log.e(TAG, "Catch Event ContentDescription  : " + event.contentDescription)
        Log.e(TAG, "Catch Event getSource : " + event.source)
        Log.e(TAG, "=========================================================================")
    }

    // 접근성 권한을 가지고, 연결이 되면 호출되는 함수
    public override fun onServiceConnected() {
        println("호출 완료!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK // 전체 이벤트 가져오기
        info.feedbackType =
            AccessibilityServiceInfo.FEEDBACK_SPOKEN or AccessibilityServiceInfo.FEEDBACK_HAPTIC
        info.notificationTimeout = 100 // millisecond
        serviceInfo = info
    }

    override fun onInterrupt() {
        // TODO Auto-generated method stub
        Log.e("TEST", "OnInterrupt")
    }

    companion object {
        private const val TAG = "AccessibilityService"
    }
}