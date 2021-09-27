package com.xht.jetpack.viewmodule

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xht.jetpack.R
import kotlinx.android.synthetic.main.activity_vm_test.*

/**
 * ViewModel
 *
 */
class VMTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vm_test)

        replaceFragment(TwoFragment())

        bt_one.setOnClickListener {
            replaceFragment(OneFragment())
        }
        bt_two.setOnClickListener {
            replaceFragment(TwoFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fl, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        Log.e("ViewModel", "VMTestActivity---onDestroy")
        super.onDestroy()
    }
}