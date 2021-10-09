package com.example.seraqchove.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seraqchove.data.data.interfaces.UserDao
import com.example.seraqchove.data.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao() : UserDao

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