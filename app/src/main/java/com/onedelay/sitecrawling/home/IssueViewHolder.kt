package com.onedelay.sitecrawling.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.base.BaseViewHolder
import com.onedelay.sitecrawling.data.model.entity.IssueItem
import kotlinx.android.synthetic.main.viewholder_issue.*

class IssueViewHolder private constructor(itemView: View, listener: BaseOnClickListener): BaseViewHolder(itemView, listener) {

    companion object {
        fun create(parent: ViewGroup, listener: BaseOnClickListener)
        = IssueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_issue, parent, false), listener)
    }

    override fun bind(item: Any) {
        if (item is IssueItem) {
            super.item = item

            tv_ranking.text = item.rank.toString()
            tv_title.text = item.name
        }
    }

}