package com.onedelay.sitecrawling.home

import com.onedelay.sitecrawling.data.model.entity.IssueItem


interface IssueContract {

    interface View {
        fun receiveNaverIssue(items: List<IssueItem>)

        fun openNewBrowser(url: String)

        fun showError(errorMessage: String)

        fun showProgress()

        fun hideProgress()
    }

    interface Actions {
        fun requestNaverIssue()

        fun clickItem(item: IssueItem)

        fun onDestroy()
    }

}