package com.onedelay.sitecrawling.news.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.model.IssueItem
import com.onedelay.sitecrawling.news.view.BaseOnClickListener
import kotlinx.android.synthetic.main.viewholder_news.*

class IssueViewHolder private constructor(itemView: View, listener: BaseOnClickListener): BaseViewHolder(itemView, listener) {
    companion object {
        fun create(parent: ViewGroup, listener: BaseOnClickListener)
        = IssueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false), listener)
    }

    override fun bind(item: Any) {
        super.item = item as IssueItem
        tv_ranking.text = item.rank.toString()
        tv_name.text = item.name
    }
}