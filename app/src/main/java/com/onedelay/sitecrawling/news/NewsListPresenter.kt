package com.onedelay.sitecrawling.news

import android.util.Log
import com.onedelay.sitecrawling.base.BaseNewsItem
import com.onedelay.sitecrawling.data.model.entity.DaumNewsItem
import com.onedelay.sitecrawling.data.model.entity.NaverNewsItem
import com.onedelay.sitecrawling.data.source.RetrofitService
import com.onedelay.sitecrawling.news.view.viewholders.DaumNewsViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.NaverNewsViewHolder
import com.onedelay.sitecrawling.util.addTo
import com.onedelay.sitecrawling.util.onMainThread
import com.onedelay.sitecrawling.util.onNetwork
import io.reactivex.disposables.CompositeDisposable

class NewsListPresenter(
        private val view: NewsContract.View,
        private var selectedPortal: Portal,
        private val retrofitService: RetrofitService = RetrofitService.create(),
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : NewsContract.UserActions {

    enum class Portal {
        Daum, Naver
    }

    override fun requestNewsList(portal: Portal?) {
        portal?: selectedPortal.let {
            selectedPortal = it

            view.showProgress()

            when (it) {
                Portal.Daum -> retrofitService.getDaumNews()
                Portal.Naver -> retrofitService.getNaverNews()
            }
                    .onNetwork()
                    .onMainThread()
                    .map { list ->
                        when (list[0]) {
                            is DaumNewsItem -> {
                                list.map { item ->
                                    (item as DaumNewsItem).run {
                                        DaumNewsViewHolder.DaumNewsItem(rank, url, title, thumbContent, thumbImageUrl)
                                    }
                                }
                            }

                            is NaverNewsItem -> {
                                list.map { item ->
                                    (item as NaverNewsItem).run {
                                        NaverNewsViewHolder.NaverNewsItem(category, url, title)
                                    }
                                }
                            }

                            else -> error("invalid data")
                        }
                    }
                    .subscribe({ list ->
                        view.receiveList(list)
                        view.hideProgress()
                    }, { throwable ->
                        Log.d("SERVER_TEST", throwable.message ?: "")
                        view.showError(throwable.message ?: "")
                        view.hideProgress()
                    }).addTo(compositeDisposable)
        }
    }

    override fun clickNewsItem(item: BaseNewsItem) {
        view.openNewBrowser(item.url)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

}