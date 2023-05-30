package com.xht.jetpack.databinding.util

import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(BindingMethod(type = ImageView::class,
    attribute = "srcCompat",
    method = "setImageResource"))
class MyBindingMethods