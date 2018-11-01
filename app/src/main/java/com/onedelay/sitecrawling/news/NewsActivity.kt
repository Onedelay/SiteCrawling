package com.onedelay.sitecrawling.news

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var sharedPref: SharedPreferences

    private lateinit var portal: String
    private lateinit var categories: List<String>

    companion object {
        val daumCategories = listOf("뉴스", "연예", "스포츠")
        val naverCategories = listOf("정치", "경제", "사회", "생활/문화", "세계", "IT/과학")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        portal = sharedPref.getString(Constants.SELECTED_PORTAL, Constants.NAVER)
        categories = when (portal) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, categories)
        container.adapter = mSectionsPagerAdapter

        for (category in categories) {
            tabs.addTab(tabs.newTab().setText(category))
        }

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    // SharedPreference 내용이 변경될 때마다 호출되는 리스너
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        categories = when (p0?.getString(p1, Constants.NAVER)) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }

        // 탭은 제거하지만 뷰페이저 내용은 안지워지는 듯 함
        tabs.removeAllTabs()
        mSectionsPagerAdapter!!.notifyDataSetChanged() // 모든탭 제거 후 notify 해주지 않으면 오류 발생

        for (category in categories) {
            tabs.addTab(tabs.newTab().setText(category))
        }

        mSectionsPagerAdapter!!.notifyDataSetChanged() // 새로 탭을 추가했기 때문에 호출해주어야 함
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
