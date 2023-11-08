package com.tc.hamie.chakib

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    suspend fun saveExercise(day: String, exerciseName: String) {
        //val exercise = exerciseRepository.getExercisesByDay(day)
        //if (exercise == null){
            val exerciseJson = """[{"exercise": "$exerciseName", "sets": []}]"""
            val dayExercise = DayExercise(day, exerciseJson)
            exerciseRepository.upsertDayExercise(dayExercise)
        //[{"exercise": "$exerciseName", "sets": []}] resultat
        /*} else {
            val existingExerciseJson = exercise.exercises
            val existingExerciseList = mutableListOf<String>()

            // Append the new exercise information to the list.
            val newExercise = """{"exercise": "$exerciseName", "sets": []}"""
            existingExerciseList.add(newExercise)

            // Convert the updated list back to a JSON string.
            val updatedExerciseJson = Gson().toJson(existingExerciseList)

            // Create a new DayExercise object with the updated JSON and upsert it.
            val dayExercise = DayExercise(day, updatedExerciseJson)
            exerciseRepository.upsertDayExercise(dayExercise)
        }*/
    }
}

