package com.tc.hamie.chakib

import androidx.annotation.DrawableRes

data class Exercise(
    val exerciseName:String,
    val subCategory:String,
    @DrawableRes val profileImage: Int
)
