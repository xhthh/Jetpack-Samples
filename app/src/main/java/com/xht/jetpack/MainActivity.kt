package com.xht.jetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.coroutine.CoroutineTestActivity
import com.xht.jetpack.databinding.ui.DbTestActivity
import com.xht.jetpack.lifecycle.LCTestActivity
import com.xht.jetpack.livedata.LDTestActivity
import com.xht.jetpack.room.RoomTestActivity
import com.xht.jetpack.viewmodule.VMTestActivity
import kotlinx.android.synthetic.main.activity_main.btnCoroutine
import kotlinx.android.synthetic.main.activity_main.btnDataBinding
import kotlinx.android.synthetic.main.activity_main.btnLifeCycle
import kotlinx.android.synthetic.main.activity_main.btnLiveData
import kotlinx.android.synthetic.main.activity_main.btnRoom
import kotlinx.android.synthetic.main.activity_main.btnViewModel

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
            skip2Activity(LDTestActivity::class.java)
        }

        //ViewModel
        btnViewModel.setOnClickListener {
            skip2Activity(VMTestActivity::class.java)
        }

        //Coroutine协程
        btnCoroutine.setOnClickListener {
            skip2Activity(CoroutineTestActivity::class.java)
        }

        //Room测试
        btnRoom.setOnClickListener {
            skip2Activity(RoomTestActivity::class.java)
        }
    }

    private fun skip2Activity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}