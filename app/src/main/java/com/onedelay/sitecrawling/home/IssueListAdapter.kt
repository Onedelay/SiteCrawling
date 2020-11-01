package com.onedelay.sitecrawling.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onedelay.sitecrawling.data.model.entity.IssueItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.base.BaseViewHolder

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