package com.xht.jetpack.viewmodule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xht.jetpack.R
import com.xht.jetpack.viewmodule.data.SharedViewModel
import kotlinx.android.synthetic.main.fragment_two.*

class TwoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: SharedViewModel = ViewModelProvider(
            requireActivity(),
            SharedViewModel.SharedViewModelFactory("纱织")
        ).get(SharedViewModel::class.java)
        model.sharedName.observe(viewLifecycleOwner, Observer {
            tv.text = it
        })
    }

    override fun onDestroy() {
        Toast.makeText(activity, "TwoFragment is destroyed", Toast.LENGTH_SHORT).show()
        Log.e("ViewModel", "TwoFragment---onDestroy")
        super.onDestroy()
    }

}