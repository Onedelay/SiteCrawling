package com.onedelay.sitecrawling.news

import com.onedelay.sitecrawling.base.BaseNewsItem

interface NewsContract {

    interface View {
        fun receiveList(items: List<BaseNewsItem>)

        fun openNewBrowser(url: String)

        fun showProgress()

        fun hideProgress()

        fun showError(message: String)
    }

    interface UserActions {
        fun requestNewsList(portal: NewsListPresenter.Portal? = null)

        fun clickNewsItem(item: BaseNewsItem)

        fun onDestroy()
    }

}