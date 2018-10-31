package com.onedelay.sitecrawling.news

import android.os.AsyncTask

abstract class NewsAsyncTask(private val listener: NewsAsyncTask.OnTaskComplete) : AsyncTask<Void, Void, List<NewsItem>>() {
    abstract fun doCrawling(): List<NewsItem>

    interface OnTaskComplete {
        fun onTaskComplete(list: List<NewsItem>?)
    }

    override fun doInBackground(vararg p0: Void?): List<NewsItem> {
        return doCrawling()
    }

    override fun onPostExecute(result: List<NewsItem>?) {
        listener.onTaskComplete(result)
    }
}