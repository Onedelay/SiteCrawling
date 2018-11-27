package com.onedelay.sitecrawling.news.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.model.NewsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_news.view.*
import kotlinx.android.synthetic.main.viewholder_top3_news.view.*

class NewsListAdapter(private val listener: OnNewsClickListener) : RecyclerView.Adapter<DefaultViewHolder>() {
    private val topCount = 3

    companion object {
        private const val VIEW_TYPE_TOP_NEWS = 1
        private const val VIEW_TYPE_NEWS = 2
    }

    private val news = ArrayList<NewsItem>()

    interface OnNewsClickListener {
        fun onNewsClick(data: NewsItem?)
        fun onNewsLongClick(data: NewsItem?)
    }

    fun setItems(items: List<NewsItem>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOP_NEWS -> TopNewsViewHolder.create(parent, listener)
            else -> NewsViewHolder.create(parent, listener)
        }
    }

    override fun getItemCount() = news.size

    override fun getItemViewType(position: Int): Int {
        return if (position < topCount) {
            VIEW_TYPE_TOP_NEWS
        } else {
            VIEW_TYPE_NEWS
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        holder.bind(news[position])
        holder.itemView.isLongClickable = true
    }
}

abstract class DefaultViewHolder(itemView: View, private val listener: NewsListAdapter.OnNewsClickListener) : RecyclerView.ViewHolder(itemView) {
    protected var item: NewsItem? = null

    init {
        itemView.setOnClickListener {
            listener.onNewsClick(item)
        }

        // 롱클릭 이벤트를 모두 처리했을 경우를 알리기 위해 true 값을 리턴하고 중지한다
        // 처리되지 않으면 false 를 반환하고, 다른 리스너는 이벤트를 계속 받는다
        itemView.setOnLongClickListener {
            listener.onNewsLongClick(item)
            true
        }
    }

    abstract fun bind(item: NewsItem)
}

class TopNewsViewHolder private constructor(itemView: View, listener: NewsListAdapter.OnNewsClickListener) : DefaultViewHolder(itemView, listener) {
    companion object {
        fun create(parent: ViewGroup, listener: NewsListAdapter.OnNewsClickListener) = TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_top3_news, parent, false), listener)
    }

    override fun bind(item: NewsItem) {
        super.item = item
        Picasso.get() // 임시 이미지
                .load(item.img)
                .into(itemView.top_imageView)
        itemView.tv_top_ranking.text = item.rank.toString()
        itemView.tv_top_name.text = item.name
        itemView.tv_top_content.text = item.content
    }
}

class NewsViewHolder private constructor(itemView: View, listener: NewsListAdapter.OnNewsClickListener) : DefaultViewHolder(itemView, listener) {
    companion object {
        fun create(parent: ViewGroup, listener: NewsListAdapter.OnNewsClickListener) = NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false), listener)
    }

    override fun bind(item: NewsItem) {
        super.item = item
        itemView.tv_ranking.text = item.rank.toString()
        itemView.tv_name.text = item.name
    }
}