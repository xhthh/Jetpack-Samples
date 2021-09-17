package com.xht.jetpack.databinding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.xht.jetpack.R
import com.xht.jetpack.databinding.ActivityViewModelBinding
import com.xht.jetpack.databinding.data.ProfileLiveDataViewModel

class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //已废弃
        //val viewModel = ViewModelProviders.of(this).get(ProfileLiveDataViewModel::class.java)

        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(ProfileLiveDataViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivityViewModelBinding>(this,
            R.layout.activity_view_model)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

}