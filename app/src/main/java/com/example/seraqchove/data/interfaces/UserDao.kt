package com.example.seraqchove.data.interfaces

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seraqchove.data.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    @Throws(SQLiteException::class)
    fun getUserByUsername(username: String) : LiveData<List<User>>

    @Query("SELECT * FROM user WHERE loggedIn = 1")
    @Throws(SQLiteException::class)
    fun getLoggedUser() : LiveData<List<User>>

    @Query("UPDATE user SET loggedIn = :status WHERE id = :userId")
    @Throws(SQLiteException::class)
    suspend fun updateUserLoggedStatus(userId: Int, status: Boolean)
}