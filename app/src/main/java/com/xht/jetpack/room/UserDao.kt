package com.xht.jetpack.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("delete from USER")
    fun deleteAll()

    @Query("SELECT * FROM USER")
    fun getAll(): List<User>

    @Query("SELECT * FROM USER WHERE id = :id")
    fun getById(id: Int): User?

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): Flow<User>
}
