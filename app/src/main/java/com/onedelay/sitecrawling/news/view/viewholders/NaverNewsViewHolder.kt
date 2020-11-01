package com.onedelay.sitecrawling.news.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.base.BaseNewsItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.base.BaseViewHolder
import kotlinx.android.synthetic.main.viewholder_naver_news.view.*


internal class NaverNewsViewHolder private constructor(itemView: View, listener: BaseOnClickListener) : BaseViewHolder(itemView, listener) {

    companion object {
        fun create(parent: ViewGroup, listener: BaseOnClickListener) =
                NaverNewsViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.viewholder_naver_news, parent, false),
                        listener
                )
    }

    class NaverNewsItem(
            val category: String,
            override val url: String,
            override val title: String
    ) : BaseNewsItem(title, url)

    override fun bind(item: Any) {
        if (item is NaverNewsItem) {
            super.item = item

            itemView.tv_category.text = item.category
            itemView.tv_title.text = item.title
        }
    }

}