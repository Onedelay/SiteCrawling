package com.onedelay.sitecrawling.news.presenter

import android.util.Log
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.news.contract.ServerContract
import com.onedelay.sitecrawling.news.model.entity.NewsItem
import com.onedelay.sitecrawling.news.model.RetrofitService
import com.onedelay.sitecrawling.util.addTo
import com.onedelay.sitecrawling.util.onMainThread
import com.onedelay.sitecrawling.util.onNetwork
import io.reactivex.disposables.CompositeDisposable

class NewsListPresenter(
        private val newsListView: ServerContract.View,
        private var portal: String,
        private var category: String
) : ServerContract.UserActions {

    private val retrofitService = RetrofitService.create()

    private val compositeDisposable = CompositeDisposable()

    override fun requestServer() {
        if (portal == Constants.NAVER) {
            retrofitService.getNaverNews(category)
                    .onNetwork()
                    .onMainThread()
                    .subscribe({
                        newsListView.receiveList(it)
                        newsListView.hideProgress()
                    }, {
                        Log.d("SERVER_TEST", it.message)
                        newsListView.showError()
                        newsListView.hideProgress()
                    }).addTo(compositeDisposable)
        } else {
            retrofitService.getDaumNews(category)
                    .onNetwork()
                    .onMainThread()
                    .subscribe({
                        newsListView.receiveList(it)
                        newsListView.hideProgress()
                    }, {
                        Log.d("SERVER_TEST", it.message)
                        newsListView.showError()
                        newsListView.hideProgress()
                    }).addTo(compositeDisposable)
        }
    }

    override fun clickNewsItem(item: NewsItem) {
        newsListView.openNewBrowser(item.url)
    }

}