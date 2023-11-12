package com.tc.hamie.chakib

import androidx.room.TypeConverter
import com.google.gson.Gson

class ExerciseArrayConverter {
    @TypeConverter
    fun fromString(value: String): Array<String> {
        // Convert the string representation back to an array
        return Gson().fromJson(value, Array<String>::class.java)
    }

    @TypeConverter
    fun toString(value: Array<String>): String {
        // Convert the array to its string representation
        return Gson().toJson(value)
    }
}