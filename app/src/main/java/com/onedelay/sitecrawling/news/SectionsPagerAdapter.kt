package com.onedelay.sitecrawling.news

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var categories: List<String>? = null

    private val pageList = ArrayList<PlaceholderFragment>()

    override fun getItem(position: Int) = pageList[position]

    override fun getCount() = categories?.size ?: 0

    override fun getPageTitle(position: Int): CharSequence? {
        return categories?.get(position)
    }

    // 프래그먼트 재사용 방지
    // https://stackoverflow.com/questions/6976027/reusing-fragments-in-a-fragmentpageradapter
    override fun getItemId(position: Int): Long {
        return categories?.get(position)?.hashCode()?.toLong() ?: 0
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setCategory(categories: List<String>, portal: String) {
        this.categories = categories
        pageList.clear()

        categories.forEach {
            pageList.add(PlaceholderFragment.newInstance(it, portal))
        }
        notifyDataSetChanged()
    }
}