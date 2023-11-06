package com.tc.hamie.chakib

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface DayExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDayExercise(exercise: DayExercise)

    @Query("SELECT * FROM DayExercises WHERE Day = :day")
    fun getExercisesByDay(day: String): DayExercise

    @Update
    suspend fun updateDayExercise(exercise: DayExercise)
}
