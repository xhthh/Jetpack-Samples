package com.xht.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "USER"
)
open class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") var email: String,
)


@Entity(
    tableName = "addresses",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Address(
    @PrimaryKey val id: Int,
    val select: String,
    val city: String,
    val state: String,
    val zip: String,
    @ColumnInfo(name = "user_id") val userId: Int,
)
