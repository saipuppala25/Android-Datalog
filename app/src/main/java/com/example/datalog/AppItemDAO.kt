package com.example.datalog

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppItemDAO {

    @Query("SELECT * from app_table order BY app_name")
    fun getAllApps() : LiveData<List<AppItemStorage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApp(app: AppItemStorage)

    @Query("DELETE FROM app_table")
    fun deleteAll()

    @Query("SELECT * FROM app_table WHERE id LIKE :id")
    suspend fun getAppById(id: Int) : AppItemStorage
}