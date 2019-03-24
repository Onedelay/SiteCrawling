package com.onedelay.sitecrawling.news.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val dividerHeight: Int
    private var divider: Drawable? = null

    init {
        // 기본인 ListView 구분선의 Drawable 을 얻는다(구분선을 커스터마이징 하고싶을 경우 여기서 Drawable 을 가져오면 된다.
        val typedArray: TypedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = typedArray.getDrawable(0)

        // 표시할 때마다 높이를 가져오지 않아도 되도록 여기서 구해둔다
        dividerHeight = divider?.intrinsicHeight ?: 0
        typedArray.recycle()
    }

//    // View 의 아이템보다 위에 그리고 싶을 경우 이 메서드를 사용한다
//    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
//        super.onDrawOver(c, parent, state)
//    }

    // View 의 아이템보다 아래에 그리고 싶을 경우 이 메서드를 사용한다
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // 좌우의 padding 으로 선의 right 와 left 를 설정
        val lineLeft = parent.paddingLeft
        val lineRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams

            // 애니메이션 등의 상황에서 제대로 이동하기 위해
            val childTransitionY = Math.round(child.translationY)
            val top = child.bottom + params.bottomMargin + childTransitionY
            val bottom = top + dividerHeight

            // View 아래에 선을 그린다
            divider?.setBounds(lineLeft, top, lineRight, bottom)
            divider?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // View 아래에 선이 들어가므로 아래에 Offset 을 넣는다
        outRect.set(0, 0, 0, dividerHeight)
    }
}