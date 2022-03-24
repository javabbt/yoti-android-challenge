package com.yoti.android.cryptocurrencychallenge.ui.assets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.ui.shared.extension.dp

class AssetsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = view.dp(8)
        outRect.top = view.dp(8)
        outRect.right = view.dp(8)
        outRect.left = view.dp(8)
    }
}