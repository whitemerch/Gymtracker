package com.tc.hamie.chakib

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull

class MainViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    suspend fun getExercises(day: String): List<ExerciseElement>? {
        val gson = Gson()
        val exercise: DayExercise? = exerciseRepository.getExercisesByDay(day).firstOrNull()

        // Return null if there's no exercise for the given day
        return exercise?.exercises?.map { gson.fromJson(it, ExerciseElement::class.java) }
    }
}