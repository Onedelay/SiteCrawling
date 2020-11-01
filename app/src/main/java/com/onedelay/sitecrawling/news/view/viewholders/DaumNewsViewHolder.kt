package com.onedelay.sitecrawling.news.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.base.BaseNewsItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.base.BaseViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_daum_news.view.*

class DaumNewsViewHolder private constructor(itemView: View, listener: BaseOnClickListener) : BaseViewHolder(itemView, listener) {

    companion object {
        fun create(parent: ViewGroup, listener: BaseOnClickListener) =
                DaumNewsViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.viewholder_daum_news, parent, false),
                        listener
                )
    }

    class DaumNewsItem(
            val rank: Int,
            override val url: String,
            override val title: String,
            val thumbContent: String,
            val thumbImageUrl: String
    ) : BaseNewsItem(title, url)

    override fun bind(item: Any) {
        if (item is DaumNewsItem) {
            super.item = item

            with(itemView) {
                tv_ranking.text = item.rank.toString()
                tv_title.text = item.title
                tv_thumb_content.text = item.thumbContent

                Picasso.get().load(item.thumbImageUrl).into(iv_thumb)
            }

        }
    }
}