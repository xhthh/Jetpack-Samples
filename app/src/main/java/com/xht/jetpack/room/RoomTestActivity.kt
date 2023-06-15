package com.xht.jetpack.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xht.jetpack.R
import kotlinx.android.synthetic.main.activity_room_test.btnDelete
import kotlinx.android.synthetic.main.activity_room_test.btnDeleteForm
import kotlinx.android.synthetic.main.activity_room_test.btnGetAll
import kotlinx.android.synthetic.main.activity_room_test.btnGetById
import kotlinx.android.synthetic.main.activity_room_test.btnInsert
import kotlinx.android.synthetic.main.activity_room_test.btnUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)

        testRoom()
    }

    var newUser: User? = null
    var id: Int = 1
    private fun testRoom() {
        val appDatabase = AppDatabase.getInstance(this)
        val userDao = appDatabase.userDao()

        btnGetAll.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val users = userDao.getAll()
                for (i in users) {
                    Log.e("room", "name = " + i.name + " email = " + i.email)
                }
            }
        }

        btnGetById.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = userDao.getById(id - 2)
                Log.e("room", "name = " + user?.name)
            }
        }

        btnInsert.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                newUser = User(id, "鸡你太美$id", "jinitaimei@qq.com")
                userDao.insert(newUser!!)
                id += 2
            }
        }

        btnUpdate.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                newUser?.email = "xxx@qq.com"
                newUser?.let { user -> userDao.update(user) }
            }
        }

        btnDelete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                newUser?.let { user -> userDao.delete(user) }
            }
        }

        btnDeleteForm.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(this@RoomTestActivity).userDao().deleteAll()
            }
        }
    }
}