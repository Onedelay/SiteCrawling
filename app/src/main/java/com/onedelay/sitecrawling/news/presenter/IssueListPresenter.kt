package com.onedelay.sitecrawling.news.presenter

import android.util.Log
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.news.contract.ServerContract
import com.onedelay.sitecrawling.news.model.IssueItem
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.model.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IssueListPresenter(private val issueListView: ServerContract.IssueView) : ServerContract.IssueActions {
    override fun requestNaverIssue() {
        RetrofitService.create().getNaverIssue().enqueue(object : Callback<List<IssueItem>> {
            override fun onFailure(call: Call<List<IssueItem>>, t: Throwable) {
                Log.d("SERVER_TEST", t.message)
                issueListView.showError()
                issueListView.hideProgress(Constants.NAVER)
            }

            override fun onResponse(call: Call<List<IssueItem>>, response: Response<List<IssueItem>>) {
                issueListView.receiveNaverIssue(response.body() ?: listOf())
                issueListView.hideProgress(Constants.NAVER)
            }
        })
    }

    override fun requestDaumIssue() {
        RetrofitService.create().getDaumIssue().enqueue(object : Callback<List<IssueItem>> {
            override fun onFailure(call: Call<List<IssueItem>>, t: Throwable) {
                Log.d("SERVER_TEST", t.message)
                issueListView.showError()
                issueListView.hideProgress(Constants.DAUM)
            }

            override fun onResponse(call: Call<List<IssueItem>>, response: Response<List<IssueItem>>) {
                issueListView.receiveDaumIssue(response.body() ?: listOf())
                issueListView.hideProgress(Constants.DAUM)
            }
        })
    }

    override fun clickItem(item: NewsItem) {
        issueListView.openNewBrowser(item.url)
    }
}