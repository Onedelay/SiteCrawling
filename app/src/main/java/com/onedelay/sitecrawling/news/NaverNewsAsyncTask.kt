package com.onedelay.sitecrawling.news

import org.jsoup.Jsoup
import java.io.IOException

class NaverNewsAsyncTask(private val category: String, private val listener: NewsAsyncTask.OnTaskComplete) : NewsAsyncTask(listener) {
    override fun doCrawling(): List<NewsItem> {
        val data = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.connect("https://news.naver.com/").get()
            val elements = doc.select("ul.section_list_ranking")
            for (element in elements) {
                if (element.parents()[0].select("h5").text() == category) {
                    for ((j, child) in element.children().withIndex()) {
                        val title = child.select("a").attr("title")
                        val url = "https://news.naver.com/" + child.select("a").attr("href")
                        data.add(NewsItem(category, j + 1, title, url))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }
}