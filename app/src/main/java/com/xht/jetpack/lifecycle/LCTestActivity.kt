package com.xht.jetpack.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.xht.jetpack.R

/**
 * LifeCycle test
 */
class LCTestActivity : AppCompatActivity() {

    private lateinit var listener: LifeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lc_test)

        listener = LifeListener()
        lifecycle.addObserver(listener)
    }


    class LifeListener : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate(owner: LifecycleOwner) {
            Log.e("TAG-----CREATE", "CREATE")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart(owner: LifecycleOwner) {
            Log.e("TAG-----START", "START")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume(owner: LifecycleOwner) {
            Log.e("TAG-----RESUME", "RESUME")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            Log.e("TAG-----DESTROY", "DESTROY")
        }
    }
}