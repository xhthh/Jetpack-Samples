package com.xht.jetpack.viewmodule.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xht.jetpack.coroutine.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel() : ViewModel() {

    constructor(sharedName: String) : this() {
        this.sharedName.value = sharedName

    }

    var sharedName: MutableLiveData<String> = MutableLiveData()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                launch(Dispatchers.IO) {
                    runCatching {
                        launch(Dispatchers.Main) {
                            log("loading")
                        }
                        log("请求接口")
                        "接口返回结果"
                    }.onSuccess {
                        log("成功回调$it")
                        launch(Dispatchers.Main) {
                            sharedName.value = it
                        }
                    }.onFailure {

                    }
                }
            }
        }
    }

    class SharedViewModelFactory(private val sharedName: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SharedViewModel(sharedName) as T
        }
    }

}