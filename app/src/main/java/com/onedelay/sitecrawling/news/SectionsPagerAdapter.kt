package com.onedelay.sitecrawling.news

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager, private val categories: List<String>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = PlaceholderFragment.newInstance(categories[position])

    override fun getCount() = categories.size
}