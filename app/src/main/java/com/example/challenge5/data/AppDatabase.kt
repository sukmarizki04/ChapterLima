package com.example.challenge5.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challenge5.data.dao.AccountDao
import com.example.challenge5.data.entity.AccountEntity

@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDatabaseDao() : AccountDao

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "challenge5.db").build()
                }
                return instance
            }
        }
    }
}