package com.xht.jetpack.livedata

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.xht.jetpack.R
import kotlinx.android.synthetic.main.activity_ld_test.*

/**
 * LiveData 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，
 * 意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。
 * 这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。
 */
class LDTestActivity : AppCompatActivity() {

    private lateinit var liveData: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ld_test)

        liveData = MutableLiveData()

        liveData.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                Toast.makeText(this@LDTestActivity, "数据变更：$t", Toast.LENGTH_SHORT).show()
                Log.e("LiveData", "数据变更：$t，当前状态：" + lifecycle.currentState)
                tvContent.text = t
            }
        })

        btnUpdate.setOnClickListener {
            liveData.value = "点击按钮，修改数据"
        }
    }

    override fun onStart() {
        super.onStart()
        liveData.value = "onStart()"
    }

    override fun onResume() {
        super.onResume()
        liveData.value = "onResume()"
    }

    override fun onPause() {
        super.onPause()
        liveData.value = "onPause()"
    }

    override fun onStop() {
        super.onStop()
        liveData.value = "onStop()"
    }

    override fun onDestroy() {
        super.onDestroy()
        liveData.value = "onDestroy()"
    }

}