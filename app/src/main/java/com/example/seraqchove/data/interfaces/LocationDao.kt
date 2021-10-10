package com.example.seraqchove.data.interfaces

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seraqchove.data.entities.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun createLocation(location : Location)

    @Query("SELECT * FROM location WHERE user_id = :userId")
    @Throws(SQLiteException::class)
    fun getLocationByUser(userId : Int) : LiveData<List<Location>>
}