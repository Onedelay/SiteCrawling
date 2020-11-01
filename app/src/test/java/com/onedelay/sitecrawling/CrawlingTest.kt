package com.onedelay.sitecrawling

import com.onedelay.sitecrawling.data.model.entity.NewsItem
import org.jsoup.Jsoup
import org.junit.Test
import java.io.IOException

class CrawlingTest {
    @Test
    fun testDaumCrawling() {
        val category = "연예"
        val data = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.connect("https://media.daum.net/society/").get()
            val element = doc.select("div.aside_g.aside_ranking").select("ul.tab_aside.tab_media")[0]
            for (item in element.children()) {
                val cat = item.select("a.link_tab").text()
                println("카테고리 : $cat")
                if (cat == category) {
                    val ch = item.select("ol.list_ranking")
                    for ((i, child) in ch.withIndex()) { // list_ranking : 3
                        for ((j, c) in child.children().withIndex()) { // 1~10
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

        println(data.toString())
    }
}