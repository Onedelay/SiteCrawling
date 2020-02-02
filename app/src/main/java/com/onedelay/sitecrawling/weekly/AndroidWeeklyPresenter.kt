package com.onedelay.sitecrawling.weekly

import com.onedelay.sitecrawling.news.model.RetrofitService
import com.onedelay.sitecrawling.news.model.entity.WeeklyItem
import com.onedelay.sitecrawling.util.addTo
import com.onedelay.sitecrawling.util.onMainThread
import com.onedelay.sitecrawling.util.onNetwork
import io.reactivex.disposables.CompositeDisposable


internal class AndroidWeeklyPresenter constructor(
        private val view: Contract.View,
        private val api: RetrofitService
) : Contract.UserAction {

    private val compositeDisposable = CompositeDisposable()

    override fun requestServer() {
        api.getAndroidWeekly()
                .onNetwork()
                .onMainThread()
                .subscribe({
                    view.receiveList(it)
                    view.hideProgress()
                }, {
                    view.showError()
                    view.hideProgress()
                }).addTo(compositeDisposable)
    }

    override fun clickWeeklyItem(item: WeeklyItem) {
        view.openNewBrowser(item.url)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}