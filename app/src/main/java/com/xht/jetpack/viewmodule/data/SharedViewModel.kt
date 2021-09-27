package com.xht.jetpack.viewmodule.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var sharedName: MutableLiveData<String> = MutableLiveData()

    init {
        sharedName.value = "雅典娜"
    }

}