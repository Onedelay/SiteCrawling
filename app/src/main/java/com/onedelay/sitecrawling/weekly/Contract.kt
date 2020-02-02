package com.onedelay.sitecrawling.weekly

import com.onedelay.sitecrawling.news.model.entity.WeeklyItem


internal interface Contract {

    interface View {
        fun receiveList(items: List<WeeklyItem>?)

        fun openNewBrowser(url: String)

        fun hideProgress()

        fun showError()
    }

    interface UserAction {
        fun requestServer()

        fun clickWeeklyItem(item: WeeklyItem)

        fun onDestroy()
    }

}