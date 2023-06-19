package com.xht.jetpack

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

class MyApplication : Application() {
    private val TAG = "MyApplication"
    override fun onCreate() {
        super.onCreate()
        mApplication = this

        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())
    }

    inner class ApplicationLifecycleObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        private fun onAppForeground() {
            Log.w(TAG, "ApplicationObserver: app moved to foreground");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        private fun onAppBackground() {
            Log.w(TAG, "ApplicationObserver: app moved to background");
        }
    }

    companion object {
        private lateinit var mApplication: MyApplication

        @JvmStatic
        fun getAppContext(): Context {
            return mApplication
        }
    }


}