package com.tc.hamie.chakib

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ExerciseRepository(private val exerciseDao: DayExerciseDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val foundExercise = MutableLiveData<DayExercise>()

    fun insertDayExercise(exercise: DayExercise) {
        coroutineScope.launch(Dispatchers.IO) {
            exerciseDao.insertDayExercise(exercise)
        }
    }

    fun updateDayExercise(exercise: DayExercise) {
        coroutineScope.launch(Dispatchers.IO) {
            exerciseDao.updateDayExercise(exercise)
        }
    }

    fun getExercisesByDay(day: String){
        coroutineScope.launch(Dispatchers.Main) {
            foundExercise.value = asyncFind(day).await()
        }
    }

    private fun asyncFind(day: String): Deferred<DayExercise?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async exerciseDao.getExercisesByDay(day)
        }

}


