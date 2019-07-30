package com.onedelay.sitecrawling.news.presenter

import android.util.Log
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.news.contract.ServerContract
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.model.RetrofitService
import com.onedelay.sitecrawling.util.addTo
import com.onedelay.sitecrawling.util.onMainThread
import com.onedelay.sitecrawling.util.onNetwork
import io.reactivex.disposables.CompositeDisposable

class IssueListPresenter(private val issueListView: ServerContract.IssueView) : ServerContract.IssueActions {
    private val retrofitService = RetrofitService.create()
    private val compositeDisposable = CompositeDisposable()

    override fun requestNaverIssue() {
        retrofitService.getNaverIssue()
                .onNetwork()
                .onMainThread()
                .subscribe({
                    issueListView.receiveNaverIssue(it)
                }, {
                    Log.d("SERVER_TEST", it.message)
                    issueListView.showError()
                }).addTo(compositeDisposable)
    }

    override fun requestDaumIssue() {
        retrofitService.getDaumIssue()
                .onNetwork()
                .onMainThread()
                .subscribe({
                    issueListView.receiveDaumIssue(it)
                }, {
                    Log.d("SERVER_TEST", it.message)
                    issueListView.showError()
                }).addTo(compositeDisposable)
    }

    override fun clickItem(item: NewsItem) {
        issueListView.openNewBrowser(item.url)
    }
}