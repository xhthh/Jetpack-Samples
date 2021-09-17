package com.xht.jetpack.databinding

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel

class TestModule : ViewModel() {
    var text: String = "this is a test"

    fun onClick(view: View) {
        Toast.makeText(view.context, "测试点击事件", Toast.LENGTH_SHORT).show()
    }
}