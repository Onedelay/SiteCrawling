package com.onedelay.sitecrawling.news.view.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.view.BaseOnClickListener
import com.onedelay.sitecrawling.news.view.NewsListAdapter

abstract class BaseViewHolder(itemView: View, private val listener: BaseOnClickListener) : RecyclerView.ViewHolder(itemView) {
    protected var item: Any? = null

    init {
        itemView.setOnClickListener {
            listener.onClick(item)
        }

        // 롱클릭 이벤트를 모두 처리했을 경우를 알리기 위해 true 값을 리턴하고 중지한다
        // 처리되지 않으면 false 를 반환하고, 다른 리스너는 이벤트를 계속 받는다
        // https://stackoverflow.com/questions/5428077/android-why-does-long-click-also-trigger-a-normal-click
        itemView.setOnLongClickListener {
            listener.onLongClick(item)
            true
        }
    }

    abstract fun bind(item: Any)
}