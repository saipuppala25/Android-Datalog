package com.example.datalog

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AppViewModel (application : Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: AppItemRepository

    var apps = mutableListOf<AppItem>()

    val allApps: LiveData<List<AppItemStorage>>

    var currentPosition = 0
    val packageManager: PackageManager = application.packageManager

    init {
        val appItemDAO = AppRoomDatabase.getDatabase(application).appDao()
        repository = AppItemRepository(appItemDAO)
        allApps = repository.allApps
    }

    fun refreshApps() {
        deleteAll()
        var count = 0
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { !it.isSystemApp }
        for (app in apps) {
            val application: AppItemStorage = AppItemStorage(count,
                app.loadLabel(packageManager).toString(),
                app.publicSourceDir)
            insert(application)
        }



    }
    private fun deleteAll() = scope.launch (Dispatchers.IO) {
        repository.deleteAll()
    }

    fun insert(app: AppItemStorage) = scope.launch (Dispatchers.IO) {
        repository.insert(app)
    }
    fun addApp(newAppItem: AppItem) {
        if (apps.contains(newAppItem)) {
            return
        }
        else {
            apps.add(newAppItem)
        }
    }


    val ApplicationInfo.isSystemApp: Boolean
        get() = (flags and ApplicationInfo.FLAG_SYSTEM) != 0
}