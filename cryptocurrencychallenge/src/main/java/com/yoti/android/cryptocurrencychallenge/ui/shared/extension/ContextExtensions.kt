package com.yoti.android.cryptocurrencychallenge.ui.shared.extension

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.math.roundToInt

fun Context.dpF(dp: Number): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)

fun Context.spF(sp: Number): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp.toFloat(),
    resources.displayMetrics
)

fun Context.dp(dp: Number): Int = dpF(dp).roundToInt()

fun Context.hideKeyboard(windowToken: IBinder? = null) {
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(
            windowToken,
            0
        )
    }
}

fun Context.getCompatDrawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.getColorAttr(@AttrRes ref: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(ref, typedValue, true)
    return typedValue.data
}


fun Context.getFont(@FontRes res: Int): Typeface {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.resources.getFont(res)
    } else {
        ResourcesCompat.getFont(this, res)
    } ?: Typeface.DEFAULT
}
