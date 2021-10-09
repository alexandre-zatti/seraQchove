package com.example.seraqchove.data.data.interfaces

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
    suspend fun createUser(user : User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username : String) : LiveData<List<User>>

}