package com.onedelay.sitecrawling.news

data class NewsItem (val category: String,
                     val rank: Int,
                     val name: String,
                     val url: String,
                     val content: String,
                     val img: String)