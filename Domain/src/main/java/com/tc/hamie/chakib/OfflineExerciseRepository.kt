package com.tc.hamie.chakib

import kotlinx.coroutines.flow.Flow

class OfflineExerciseRepository (private val dayExerciseDao: DayExerciseDao) : ExerciseRepository  {

    override suspend fun upsertDayExercise(exercise: DayExercise) = dayExerciseDao.upsertDayExercise(exercise)

    override fun getExercisesByDay(day: String): DayExercise = dayExerciseDao.getExercisesByDay(day)

}