package com.xht.jetpack.databinding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.databinding.ActivityViewBindingBinding

class ViewBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvContent.text = "视图绑定"
    }
}