package com.example.seraqchove.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.interfaces.UserDao
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.data.interfaces.LocationDao

@Database(entities = [User::class, Location::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao() : UserDao
    abstract fun locationDao() : LocationDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                                                    AppDatabase::class.java,
                                                    "appDatabase").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}