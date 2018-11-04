package com.onedelay.sitecrawling.news.crawler

import android.util.Log
import com.onedelay.sitecrawling.news.NewsItem
import org.jsoup.Jsoup
import java.io.IOException

class DaumNewsAsyncTask(private val category: String, listener: OnTaskComplete) : NewsAsyncTask(listener) {
    override fun doCrawling(): List<NewsItem> {
        val data = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.connect("https://media.daum.net/society/").get()
            val element = doc.select("div.aside_g.aside_ranking").select("ul.tab_aside.tab_media")[0]
            for (item in element.children()) {
                val cat = item.select("a.link_tab").text()
                if (cat == category) {
                    val ch = item.select("ol.list_ranking")
                    for ((i, child) in ch.withIndex()) {
                        for ((j, c) in child.children().withIndex()) {
                            val title = c.select("strong.tit_g").select("a").text().trim()
                            val url = c.select("strong.tit_g").select("a").attr("href")
                            data.add(NewsItem(category, (i * 10) + (j + 1), title, url))
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }
}