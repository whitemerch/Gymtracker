package com.tc.hamie.chakib

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class MainViewModel() :
    ViewModel() {

    //private val repository: ExerciseRepository
    var foundExercise = MutableLiveData<DayExercise>()


    /*init {
        val exerciseDb = ExerciseRoomDatabase.getInstance(application)
        val exerciseDao = exerciseDb.exerciseDao()
        repository = ExerciseRepository(exerciseDao)


        foundExercise = repository.foundExercise
    }


    fun getExercisesByDay(day: String){
        repository.getExercisesByDay(day)
    }*/

}