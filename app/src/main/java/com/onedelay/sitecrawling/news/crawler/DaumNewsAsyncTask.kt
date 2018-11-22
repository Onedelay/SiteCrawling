package com.onedelay.sitecrawling.news.crawler

import com.onedelay.sitecrawling.news.model.NewsItem
import org.jsoup.Jsoup
import java.io.IOException

@Deprecated("Not use")
class DaumNewsAsyncTask(private val category: String, listener: OnTaskComplete) : NewsAsyncTask(listener) {
    override fun doCrawling(): List<NewsItem> {
        var count = 0
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
                            val content by lazy {
                                if (count++ < 4) {
                                    // 해당 url 클릭해서 내용 일부 발췌
                                    // 3개까지만 실행하도록 분기 (너무 오래 걸림)
                                    Jsoup.connect(url).get().select("meta[property=og:description]").attr("content")
                                } else {
                                    ""
                                }
                            }
                            data.add(NewsItem(category, (i * 10) + (j + 1), title, url, content, "https://t1.daumcdn.net/daumtop_chanel/op/20170315064553027.png"))
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