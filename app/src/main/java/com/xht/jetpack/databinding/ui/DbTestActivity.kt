package com.xht.jetpack.databinding.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.xht.jetpack.BR
import com.xht.jetpack.R
import com.xht.jetpack.databinding.CustomBinding
import com.xht.jetpack.databinding.bean.User
import com.xht.jetpack.databinding.data.TestModule

/**
 * DataBinding 使用
 */
class DbTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //所传泛型参数即由布局生成的绑定类，类名为：下划线语法变程驼峰语法+Binding
        val binding = DataBindingUtil.setContentView<CustomBinding>(this,
            R.layout.activity_databinding)
        binding.user = User("xht", 18)
        //另一种设置方式，报错找不到 BR，在 build.gradle 中引入 kotlin-kapt
        //binding.setVariable(BR.user, User("Jack", 23))

        binding.setVariable(BR.title, "DataBindingTest")
        binding.viewModel = TestModule()

        binding.btnLiveData.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }

        binding.btnObservable.setOnClickListener {
            startActivity(Intent(this, ObservableFieldActivity::class.java))
        }
    }

    //todo 在activity 中写的点击事件在布局中调不了，为啥？
    fun onClick(view: View) {
        //Toast.makeText(this, textView.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "点击test", Toast.LENGTH_SHORT).show()
    }
}