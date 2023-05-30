package com.xht.jetpack.databinding.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.xht.jetpack.R
import com.xht.jetpack.databinding.data.Popularity

object BindingAdapters {

    @BindingAdapter("popularityIcon")
    @JvmStatic
    fun popularityIcon(view: ImageView, popularity: Popularity) {
        val color = getAssociatedColor(popularity, view.context)
        ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))
        view.setImageDrawable(getDrawablePopularity(popularity, view.context))
    }

    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    fun setImageUrl(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        if (url == null) {
            imageView.setImageDrawable(placeHolder);
        } else {

        }
    }


    @BindingAdapter("android:onLayoutChange")
    fun setOnLayoutChangeListener(
        view: View,
        oldValue: View.OnLayoutChangeListener?,
        newValue: View.OnLayoutChangeListener?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue)
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue)
            }
        }
    }


    private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
        return when (popularity) {
            Popularity.NORMAL -> context.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.colorForeground)
            ).getColor(0, 0x000000)
            Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
            Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
        }
    }

    private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
        return when (popularity) {
            Popularity.NORMAL -> {
                ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
            }
            Popularity.POPULAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
            Popularity.STAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
        }
    }

    @BindingAdapter("hideIfZero")
    @JvmStatic
    fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }

    @BindingAdapter("progressTint")
    @JvmStatic
    fun tintPopularity(view: ProgressBar, popularity: Popularity) {
        val color = getAssociatedColor(popularity, view.context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = ColorStateList.valueOf(color)
        }
    }

    @BindingAdapter(value = ["progressScaled", "android:max"], requireAll = true)
    @JvmStatic
    fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
        progressBar.progress = (likes * max / 5).coerceAtMost(max)
    }
}