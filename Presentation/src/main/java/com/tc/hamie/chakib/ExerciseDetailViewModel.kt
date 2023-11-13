package com.tc.hamie.chakib

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.firstOrNull
import com.google.gson.Gson
class ExerciseDetailViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel(){
    suspend fun addReps(exerciseObject: ExerciseElement,
                        weight: Int,
                        repetitions: Int,
                        date: String): ExerciseElement
    {
        // Create a new Set with the provided weight and repetitions
        val newSet = Setstructure(repetitions, weight)

        // Create an updated ExerciseElement with the new set
        val updatedExerciseElement = ExerciseElement(
            exerciseObject.exercise,
            exerciseObject.sets.toMutableList().apply { add(newSet) },
            exerciseObject.profileImage
        )

        // Get the existing exercises for the given date from the repository
        val exercise: DayExercise? = exerciseRepository.getExercisesByDay(date).firstOrNull()
        val gson = Gson()

        // Map the existing exercises to ExerciseElement objects
        val listExercises = exercise?.exercises?.map { gson.fromJson(it, ExerciseElement::class.java) }

        // Update the specific ExerciseElement in the list with the new sets
        val updatedListExercises = listExercises?.map {
            if (it.exercise == exerciseObject.exercise) {
                updatedExerciseElement
            } else {
                it
            }
        }

        // Create an updated DayExercise with the updated list of ExerciseElements
        val updatedDayExercise =
            updatedListExercises?.map { gson.toJson(it) }
                ?.let { DayExercise(date, it.toTypedArray()) }

        // Upsert the updated DayExercise to the repository
        if (updatedDayExercise != null) {
            exerciseRepository.upsertDayExercise(updatedDayExercise)
        }
        return updatedExerciseElement
    }

    suspend fun deleteRep(
        set: Setstructure,
        exerciseObject: ExerciseElement,
        date: String
    ) : ExerciseElement {
        // Filter out the set to be deleted from the sets list in ExerciseElement
        val updatedSets = exerciseObject.sets.filterNot { it == set }

        // Create an updated ExerciseElement with the filtered sets
        val updatedExerciseElement = ExerciseElement(
            exerciseObject.exercise,
            updatedSets.toMutableList(),
            exerciseObject.profileImage
        )

        // Get the existing exercises for the given date from the repository
        val exercise: DayExercise? = exerciseRepository.getExercisesByDay(date)?.firstOrNull()
        val gson = Gson()

        // Map the existing exercises to ExerciseElement objects
        val listExercises = exercise?.exercises?.map { gson.fromJson(it, ExerciseElement::class.java) }

        // Update the specific ExerciseElement in the list with the new sets
        val updatedListExercises = listExercises?.map {
            if (it.exercise == exerciseObject.exercise) {
                updatedExerciseElement
            } else {
                it
            }
        }

        // Create an updated DayExercise with the updated list of ExerciseElements
        val updatedDayExercise =
            updatedListExercises?.map { gson.toJson(it) }
                ?.let { DayExercise(date, it.toTypedArray()) }

        // Upsert the updated DayExercise to the repository
        if (updatedDayExercise != null) {
            exerciseRepository.upsertDayExercise(updatedDayExercise)
        }
        return updatedExerciseElement
    }
}