package com.onedelay.sitecrawling.news.presenter

import android.util.Log
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.model.RetrofitService
import com.onedelay.sitecrawling.news.contract.NewsListContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListPresenter(private val newsListView: NewsListContract.View,
                        private var portal: String,
                        private var category: String) : NewsListContract.UserActions {

    override fun requestServer() {
        if (portal == Constants.NAVER) {
            RetrofitService.create().getNaverNews(category).enqueue(object : Callback<List<NewsItem>> {
                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                    Log.d("SERVER_TEST", t.message)
                    newsListView.showError()
                    newsListView.hideProgress()
                }

                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
                    val body = response.body()
                    newsListView.receiveNewsList(body)
                    newsListView.hideProgress()
                }
            })
        } else {
            RetrofitService.create().getDaumNews(category).enqueue(object : Callback<List<NewsItem>> {
                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                    Log.d("SERVER_TEST", t.message)
                    newsListView.showError()
                    newsListView.hideProgress()
                }

                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
                    val body = response.body()
                    newsListView.receiveNewsList(body)
                    newsListView.hideProgress()
                }
            })
        }
    }

    override fun clickNewsItem(item: NewsItem) {
        newsListView.openNewBrowser(item.url)
    }
}