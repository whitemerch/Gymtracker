package com.tc.hamie.chakib

import android.app.Application

class ExerciseApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
