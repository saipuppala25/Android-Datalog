package com.example.datalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppViewModel (application : Application) : AndroidViewModel(application) {

    var apps = mutableListOf<AppItem>()

    fun addApp(newAppItem: AppItem) {
        if (apps.contains(newAppItem)) {
            return
        }
        else {
            apps.add(newAppItem)
        }
    }
}