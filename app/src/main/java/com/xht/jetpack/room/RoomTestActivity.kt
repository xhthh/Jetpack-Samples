package com.xht.jetpack.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xht.jetpack.R

class RoomTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)

        testRoom()
    }

    private fun testRoom() {
        val appDatabase = AppDatabase.getInstance(this)
        val userDao = appDatabase.userDao()

        val users = userDao.getAll()
        val user = userDao.getById(1)
        val newUser = User(2, "鸡你太美", "jinitaimei@qq.com")
        userDao.insert(newUser)
        newUser.email = "jinitaimei@qq.com"
        userDao.update(newUser)
        userDao.delete(newUser)
    }
}