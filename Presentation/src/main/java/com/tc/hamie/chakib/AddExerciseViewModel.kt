package com.tc.hamie.chakib

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.firstOrNull

class AddExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    suspend fun saveExercise(day: String, exerciseName: String, profileImage: Int) {
        val exercise: DayExercise? = exerciseRepository.getExercisesByDay(day).firstOrNull()

        if (exercise == null) {
            // If there is no existing DayExercise for the given day, create a new one
            val exerciseJson = """{"exercise": "$exerciseName", "sets": [], "profileImage": $profileImage}"""
            val dayExercise = DayExercise(day, arrayOf(exerciseJson))
            exerciseRepository.upsertDayExercise(dayExercise)
        } else {
            // If there is an existing DayExercise, update the exercises list if exerciseName is not already in the list
            val existingList = exercise.exercises.toMutableList()

            // Check if exerciseName is already in the list
            if (!existingList.any { it.contains("\"exercise\": \"$exerciseName\"") }) {
                val newExerciseJson = """{"exercise": "$exerciseName", "sets": [], "profileImage": $profileImage}"""
                existingList.add(newExerciseJson)

                val updatedDayExercise = DayExercise(day, existingList.toTypedArray())
                exerciseRepository.upsertDayExercise(updatedDayExercise)
            }
            // else: exerciseName is already in the list, do nothing
        }
    }


}

