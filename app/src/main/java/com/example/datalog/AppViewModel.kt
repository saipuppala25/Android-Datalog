package com.example.datalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppViewModel (application : Application) : AndroidViewModel(application) {

    var apps = emptyList<AppItem>()

}