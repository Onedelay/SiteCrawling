package com.onedelay.sitecrawling

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_image.view.*

class ImageListAdapter : RecyclerView.Adapter<ImageViewHolder>() {
    private val images = ArrayList<ImageItem>()

    fun setItems(items: List<ImageItem>) {
        images.clear()
        images.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder.create(parent)

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }
}

class ImageViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var item: ImageItem? = null

    companion object {
        fun create(parent: ViewGroup) = ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_image, parent, false))
    }

    fun bind(item: ImageItem) {
        this.item = item
        itemView.textView.text = item.name
        Picasso.get()
                .load(item.url)
                .into(itemView.imageView)
    }
}