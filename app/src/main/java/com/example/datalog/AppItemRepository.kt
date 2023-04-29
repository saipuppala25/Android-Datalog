package com.example.datalog

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AppItemRepository(private val appDAO: AppItemDAO) {
    val allApps: LiveData<List<AppItemStorage>> = appDAO.getAllApps()

    @WorkerThread
    fun insert(app: AppItemStorage) {
        appDAO.insertApp(app)
    }

    @WorkerThread
    fun deleteAll() {
        appDAO.deleteAll()
    }

    @WorkerThread
    suspend fun getAppById(id: Int): AppItemStorage {
        return appDAO.getAppById(id)
    }
}