package com.tc.hamie.chakib

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(DayExercise::class)], version = 1, exportSchema = false)
abstract class ExerciseRoomDatabase : RoomDatabase() {
    abstract fun dayExerciseDao() : DayExerciseDao


    companion object {
        @Volatile
        private var Instance: ExerciseRoomDatabase? = null
        fun getDatabase(context: Context): ExerciseRoomDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExerciseRoomDatabase::class.java, "DayExercises")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}


