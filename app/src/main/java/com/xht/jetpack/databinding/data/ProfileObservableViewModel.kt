package com.xht.jetpack.databinding.data

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.xht.jetpack.BR
import com.xht.jetpack.databinding.util.ObservableViewModel

class ProfileObservableViewModel : ObservableViewModel() {
    val name = ObservableField("南")
    val lastName = ObservableField("浅仓")
    val likes = ObservableInt(0)

    fun onLike() {
        likes.increment()

        notifyPropertyChanged(BR.popularity)
    }

    @Bindable
    fun getPopularity(): Popularity {
        return likes.get().let {
            when {
                it > 9 -> Popularity.STAR
                it > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }
        }
    }

}

private fun ObservableInt.increment() {
    set(get() + 1)
}

