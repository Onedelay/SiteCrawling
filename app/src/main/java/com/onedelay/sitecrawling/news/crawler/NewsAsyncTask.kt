package com.onedelay.sitecrawling.news.crawler

import android.os.AsyncTask
import com.onedelay.sitecrawling.news.model.NewsItem

/**
 * @Deprecated 크롤링은 웹 서버에서 진행합니다.
 */
@Deprecated("Not use")
abstract class NewsAsyncTask(private val listener: OnTaskComplete) : AsyncTask<Void, Void, List<NewsItem>>() {
    protected abstract fun doCrawling(): List<NewsItem>

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