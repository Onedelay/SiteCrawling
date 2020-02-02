package com.onedelay.sitecrawling.news.contract

import com.onedelay.sitecrawling.news.model.entity.IssueItem
import com.onedelay.sitecrawling.news.model.entity.NewsItem

interface ServerContract {

    interface View {
        fun receiveList(items: List<NewsItem>?)

        fun openNewBrowser(url: String)

        fun hideProgress()

        fun showError()
    }

    interface UserActions {
        fun requestServer()

        fun clickNewsItem(item: NewsItem)
    }

    interface IssueView {
        fun receiveNaverIssue(items: List<IssueItem>)

        fun receiveDaumIssue(items: List<IssueItem>)

        fun openNewBrowser(url: String)

        fun showError()

        fun hideProgress(type: String)
    }

    interface IssueActions {
        fun requestNaverIssue()

        fun requestDaumIssue()

        fun clickItem(item: NewsItem)
    }

}