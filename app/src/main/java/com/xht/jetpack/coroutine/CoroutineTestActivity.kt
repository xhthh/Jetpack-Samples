package com.xht.jetpack.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.R
import kotlinx.coroutines.*

/**
 * 协程测试
 */
class CoroutineTestActivity : AppCompatActivity(),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    //private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        launch {
            repeat(5) {
                delay(1000L * it)
                log(it)
            }
        }
        log("Activity Created")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        log("Activity Destroyed")
    }
}