package com.tc.hamie.chakib

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DayExercises")
data class DayExercise(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var day: String,

    var exercises: Array<String>

)