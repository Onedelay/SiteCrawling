package com.onedelay.sitecrawling.home

import android.util.Log
import com.onedelay.sitecrawling.data.source.RetrofitService
import com.onedelay.sitecrawling.data.model.entity.IssueItem
import com.onedelay.sitecrawling.util.addTo
import com.onedelay.sitecrawling.util.onMainThread
import com.onedelay.sitecrawling.util.onNetwork
import io.reactivex.disposables.CompositeDisposable

class IssueListPresenter(
        private val view: IssueContract.View,
        private val retrofitService: RetrofitService = RetrofitService.create(),
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : IssueContract.Actions {

    override fun requestNaverIssue() {
        view.showProgress()
        retrofitService.getNaverIssue()
                .onNetwork()
                .onMainThread()
                .subscribe({
                    view.hideProgress()
                    view.receiveNaverIssue(it)
                }, {
                    Log.d("SERVER_TEST", it.message ?: "")
                    view.showError(it.message ?: "")
                }).addTo(compositeDisposable)
    }

    override fun clickItem(item: IssueItem) {
        view.openNewBrowser(item.url)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

}