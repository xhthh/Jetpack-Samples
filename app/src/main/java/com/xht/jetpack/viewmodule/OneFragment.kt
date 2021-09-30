package com.xht.jetpack.viewmodule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.xht.jetpack.R
import com.xht.jetpack.viewmodule.data.SharedViewModel
import kotlinx.android.synthetic.main.fragment_one.*

class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val model: SharedViewModel =
//            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val model by activityViewModels<SharedViewModel>()
//        val model: SharedViewModel = ViewModelProvider(
//            requireActivity(),
//            SharedViewModel.SharedViewModelFactory("")
//        ).get(SharedViewModel::class.java)
        bt_fragment_one.setOnClickListener {
            model.sharedName.value = "星矢"

            val result = model.sharedName.value
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
    }

    override fun onDestroy() {
        Toast.makeText(activity, "OneFragment is destroyed", Toast.LENGTH_SHORT).show()
        Log.e("ViewModel", "OneFragment---onDestroy")
        super.onDestroy()
    }

}