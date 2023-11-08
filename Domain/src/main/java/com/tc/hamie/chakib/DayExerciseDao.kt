package com.tc.hamie.chakib

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface DayExerciseDao {
    @Upsert
    suspend fun upsertDayExercise(exercise: DayExercise)

    @Query("SELECT * FROM DayExercises WHERE Day = :day")
    fun getExercisesByDay(day: String): DayExercise
}
