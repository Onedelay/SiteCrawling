package com.onedelay.sitecrawling.news.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.view.NewsListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_top3_news.view.*

class TopNewsViewHolder private constructor(itemView: View, listener: NewsListAdapter.OnNewsClickListener) : BaseViewHolder(itemView, listener) {
    companion object {
        fun create(parent: ViewGroup, listener: NewsListAdapter.OnNewsClickListener) = TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_top3_news, parent, false), listener)
    }

    override fun bind(item: Any) {
        super.item = item as NewsItem
        Picasso.get() // 임시 이미지
                .load(item.img)
                .into(itemView.top_imageView)
        itemView.tv_top_ranking.text = item.rank.toString()
        itemView.tv_top_name.text = item.name
        itemView.tv_top_content.text = item.content
    }
}