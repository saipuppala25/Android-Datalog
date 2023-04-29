package com.example.datalog

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppItemStorage::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun appDao(): AppItemDAO

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): AppRoomDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "App_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}