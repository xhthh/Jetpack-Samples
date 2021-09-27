package com.xht.jetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.databinding.ui.DbTestActivity
import com.xht.jetpack.lifecycle.LCTestActivity
import com.xht.jetpack.viewmodule.VMTestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DataBinding
        btnDataBinding.setOnClickListener {
            skip2Activity(DbTestActivity::class.java)
        }

        //LifeCycle
        btnLifeCycle.setOnClickListener {
            skip2Activity(LCTestActivity::class.java)
        }

        //LiveData
        btnLiveData.setOnClickListener {
            skip2Activity(VMTestActivity::class.java)
        }

        //ViewModel
        btnViewModel.setOnClickListener {
            skip2Activity(VMTestActivity::class.java)
        }
    }

    private fun skip2Activity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}