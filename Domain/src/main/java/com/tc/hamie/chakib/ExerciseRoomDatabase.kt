package com.tc.hamie.chakib

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Exercise::class)], version = 1, exportSchema = false)
abstract class ExerciseRoomDatabase : RoomDatabase() {

    abstract fun exerciseDao(): DayExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseRoomDatabase? = null

        fun getInstance(context: Context): ExerciseRoomDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseRoomDatabase::class.java,
                        "exercise_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}