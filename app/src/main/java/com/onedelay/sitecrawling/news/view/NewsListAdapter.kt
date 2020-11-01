package com.onedelay.sitecrawling.news.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onedelay.sitecrawling.base.BaseNewsItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.base.BaseViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.DaumNewsViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.NaverNewsViewHolder

class NewsListAdapter(private val listener: BaseOnClickListener) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_DAUM_NEWS = 1
        private const val VIEW_TYPE_NAVER_NEWS = 2
    }

    private val news = ArrayList<BaseNewsItem>()

    fun setItems(items: List<BaseNewsItem>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_DAUM_NEWS -> DaumNewsViewHolder.create(parent, listener)
            VIEW_TYPE_NAVER_NEWS -> NaverNewsViewHolder.create(parent, listener)
            else -> error("invalid view type")
        }
    }

    override fun getItemCount() = news.size

    override fun getItemViewType(position: Int): Int {
        return when (news[position]) {
            is DaumNewsViewHolder.DaumNewsItem -> VIEW_TYPE_DAUM_NEWS
            is NaverNewsViewHolder.NaverNewsItem -> VIEW_TYPE_NAVER_NEWS
            else -> error("invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(news[position])
        holder.itemView.isLongClickable = true
    }

}