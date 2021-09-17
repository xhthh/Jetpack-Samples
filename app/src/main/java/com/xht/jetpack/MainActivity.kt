package com.xht.jetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.databinding.ui.DbTestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDataBinding.setOnClickListener {
            skip2Activity(DbTestActivity::class.java)
        }
    }

    private fun skip2Activity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}