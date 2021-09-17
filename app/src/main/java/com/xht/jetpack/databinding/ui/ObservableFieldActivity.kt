package com.xht.jetpack.databinding.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.xht.jetpack.R
import com.xht.jetpack.databinding.ActivityObservableFiledBinding
import com.xht.jetpack.databinding.data.ObservableFieldProfile

class ObservableFieldActivity : AppCompatActivity() {

    private val observableFieldProfile = ObservableFieldProfile("南", "浅仓", ObservableInt(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityObservableFiledBinding>(this,
            R.layout.activity_observable_filed)
        binding.user = observableFieldProfile
    }

    fun onLike(view: View) {
        observableFieldProfile.likes.set(observableFieldProfile.likes.get() + 1)
    }
}