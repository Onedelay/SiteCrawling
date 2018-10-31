package com.onedelay.sitecrawling.news

class DaumNewsAsyncTask(private val category: String, listener: NewsAsyncTask.OnTaskComplete) : NewsAsyncTask(listener) {
    override fun doCrawling(): List<NewsItem> {
        // Do nothing yet
        return listOf()
    }
}