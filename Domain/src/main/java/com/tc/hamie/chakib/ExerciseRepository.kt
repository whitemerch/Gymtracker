package com.tc.hamie.chakib

import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    /**
     * Insert item in the data source
     */
    suspend fun upsertDayExercise(exercise: DayExercise)

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getExercisesByDay(day: String): Flow<DayExercise?>

}

