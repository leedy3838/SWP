package com.example.androidstudioproject

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.help_screen.*
import android.os.Bundle as Bundle1

class HelpScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_screen)

        /* 여백, 너비에 대한 정의 */
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth) // dimen 파일이 없으면 생성해야함
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager_help.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        viewPager_help.offscreenPageLimit = 1
        viewPager_help.adapter = ViewPagerAdapter(getHelpList())
        viewPager_help.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        spring_dots_indicator.setViewPager2(viewPager_help) // indicator 설정

    }


    // 뷰 페이저에 들어갈 아이템
    private fun getHelpList(): ArrayList<Int> {
        return arrayListOf(R.drawable.helpscreen1, R.drawable.helpscreen2, R.drawable.helpscreen1, R.drawable.helpscreen2)
    }
}
