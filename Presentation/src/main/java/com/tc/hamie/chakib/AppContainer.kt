package com.tc.hamie.chakib

import android.content.Context

interface AppContainer {
    val exerciseRepository: ExerciseRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val exerciseRepository: ExerciseRepository by lazy {
        OfflineExerciseRepository(ExerciseRoomDatabase.getDatabase(context).dayExerciseDao())
    }
}
