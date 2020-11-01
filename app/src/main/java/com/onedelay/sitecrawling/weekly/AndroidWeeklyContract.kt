package com.onedelay.sitecrawling.weekly

import com.onedelay.sitecrawling.data.model.entity.WeeklyItem


internal interface AndroidWeeklyContract {

    interface View {
        fun receiveList(items: List<WeeklyItem>)

        fun openNewBrowser(url: String)

        fun showProgress()

        fun hideProgress()

        fun showError(message: String)
    }

    interface UserAction {
        fun requestAndroidWeekly()

        fun clickWeeklyItem(item: WeeklyItem)

        fun onDestroy()
    }

}