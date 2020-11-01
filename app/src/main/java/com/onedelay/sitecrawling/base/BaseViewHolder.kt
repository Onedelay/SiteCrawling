package com.onedelay.sitecrawling.base

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(itemView: View, private val listener: BaseOnClickListener) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    // LayoutContainer containerView 변수를 오버라이딩해야 kotlin extension 동작(캐시에서 가져옴)
    // https://www.androidhuman.com/lecture/kotlin/2017/11/26/kotlin_android_extensions_on_viewholder/
    protected var item: Any? = null
    override val containerView: View?
        get() = itemView

    init {
        itemView.setOnClickListener {
            listener.onClick(item)
        }

        // 롱클릭 이벤트를 모두 처리했을 경우를 알리기 위해 true 값을 리턴하고 중지한다
        // 처리되지 않으면 false 를 반환하고, 다른 리스너는 이벤트를 계속 받는다
        // https://stackoverflow.com/questions/5428077/android-why-does-long-click-also-trigger-a-normal-click
        itemView.setOnLongClickListener {
            listener.onLongClick(item)
            true
        }
    }

    abstract fun bind(item: Any)
}