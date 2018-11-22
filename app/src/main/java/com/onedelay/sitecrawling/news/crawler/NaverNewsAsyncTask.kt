package com.onedelay.sitecrawling.news.crawler

import com.onedelay.sitecrawling.news.model.NewsItem
import org.jsoup.Jsoup
import java.io.IOException

@Deprecated("Not use")
class NaverNewsAsyncTask(private val category: String, listener: OnTaskComplete) : NewsAsyncTask(listener) {
    override fun doCrawling(): List<NewsItem> {
        var count = 0
        val data = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.connect("https://news.naver.com/").get()
            val elements = doc.select("ul.section_list_ranking")
            for (element in elements) {
                if (element.parents()[0].select("h5").text() == category) {
                    for ((j, child) in element.children().withIndex()) {
                        val title = child.select("a").attr("title")
                        val url = "https://news.naver.com/" + child.select("a").attr("href")
                        val content by lazy {
                            if (count++ < 4) {
                                // 해당 url 클릭해서 내용 일부 발췌
                                // 3개까지만 실행하도록 분기 (너무 오래 걸림)
                                Jsoup.connect(url).get().select("meta[property=og:description]").attr("content")
                            } else {
                                ""
                            }
                        }
                        data.add(NewsItem(category, j + 1, title, url, content, "https://t1.daumcdn.net/cfile/tistory/2212C6335790D35004"))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }
}