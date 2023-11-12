package com.tc.hamie.chakib

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(exerciseApplication().container.exerciseRepository)
        }

        initializer {
            AddExerciseViewModel(exerciseApplication().container.exerciseRepository)
        }
    }
}


fun CreationExtras.exerciseApplication(): ExerciseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExerciseApplication)
