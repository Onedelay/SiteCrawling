package com.onedelay.sitecrawling.news.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.view.adapters.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var sharedPref: SharedPreferences

    private lateinit var portal: String

    companion object {
        val daumCategories = listOf("뉴스", "연예", "스포츠")
        val naverCategories = listOf("정치", "경제", "사회", "생활/문화", "세계", "IT/과학")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        portal = sharedPref.getString(Constants.SELECTED_PORTAL, Constants.NAVER)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mSectionsPagerAdapter?.setCategory(when (portal) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }, portal)

        /* 뷰페이저 특성상 이웃한 탭의 프래그먼트까지 함께 생성하므로 총 3개의 프래그먼트가 아래와 같은 순서로 생성된다
         * 1. 선택된 프래그먼트
         * 2. 이웃한 프래그먼트 (왼쪽부터 하는 듯 하다)
         */
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        tabs.setupWithViewPager(container, true)
    }

    // SharedPreference 내용이 변경될 때마다 호출되는 리스너
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        mSectionsPagerAdapter!!.setCategory(when (p0?.getString(p1, Constants.NAVER)) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }, portal)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // 어떤 portal 의 뉴스를 보여줄 지 옵션메뉴에서 설정
        portal = when (id) {
            R.id.daum -> Constants.DAUM
            else -> Constants.NAVER
        }

        // shared preference 내용 변경
        with(sharedPref.edit()) {
            putString(Constants.SELECTED_PORTAL, portal)
            apply()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sharedPref.unregisterOnSharedPreferenceChangeListener(this)
    }
}
