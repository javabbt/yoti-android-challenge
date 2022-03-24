package com.yoti.android.cryptocurrencychallenge.ui.shared.extension

import android.view.View

fun View.dpF(dp: Number): Float = context.dpF(dp)
fun View.dp(dp: Number): Int = context.dp(dp)

@JvmOverloads
fun <T : View> T.execute(delay: Long = -1L, block: T.() -> Unit) {
    if (delay == -1L) this.post {
        this.block()
    } else this.postDelayed({
        this.block()
    }, delay)
}
