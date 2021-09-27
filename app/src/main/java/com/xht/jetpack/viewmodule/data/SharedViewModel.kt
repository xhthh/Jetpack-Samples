package com.xht.jetpack.viewmodule.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SharedViewModel(sharedName: String) : ViewModel() {

    var sharedName: MutableLiveData<String> = MutableLiveData()

    init {
        this.sharedName.value = sharedName
    }

    class SharedViewModelFactory(private val sharedName: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SharedViewModel(sharedName) as T
        }
    }

}