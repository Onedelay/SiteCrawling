package com.onedelay.sitecrawling.news.view.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.onedelay.sitecrawling.news.view.NewsListFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val categories: ArrayList<String> = ArrayList()
    private val pageList = ArrayList<NewsListFragment>()

    override fun getItem(position: Int) = pageList[position]

    override fun getCount() = categories.size

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position]
    }

    // 프래그먼트 재사용 방지
    // https://stackoverflow.com/questions/6976027/reusing-fragments-in-a-fragmentpageradapter
    override fun getItemId(position: Int): Long {
        return categories[position].hashCode().toLong()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setCategory(categories: List<String>, portal: String) {
        this.categories.clear()
        this.categories.addAll(categories)
        pageList.clear()
        pageList.addAll(categories.map { NewsListFragment.newInstance(it, portal) })
        notifyDataSetChanged()
    }
}