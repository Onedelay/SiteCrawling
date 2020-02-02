package com.onedelay.sitecrawling.news.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.onedelay.sitecrawling.news.model.entity.IssueItem
import com.onedelay.sitecrawling.news.view.BaseOnClickListener
import com.onedelay.sitecrawling.news.view.viewholders.BaseViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.IssueViewHolder

class IssueListAdapter(private val listener: BaseOnClickListener) : RecyclerView.Adapter<BaseViewHolder>() {
    private val issue = ArrayList<IssueItem>()

    fun setItems(items: List<IssueItem>) {
        issue.clear()
        issue.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssueViewHolder.create(parent, listener)

    override fun getItemCount() = issue.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(issue[position])
        holder.itemView.isLongClickable = true
    }
}