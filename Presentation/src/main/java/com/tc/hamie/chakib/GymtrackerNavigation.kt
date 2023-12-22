package com.tc.hamie.chakib

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymTrackerAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xff75d4bf)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: "Day"

    Scaffold(
        topBar = {
            GymTrackerAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Day",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "Day") {
                MainViewApp(
                    addExerciseButton = {
                        navController.navigate("AddExercise/$it")
                    },
                    exerciseDetailButton = { param1, param2 ->
                        navController.navigate("ExerciseDetail/$param1/$param2")
                    },
                            modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = "AddExercise/{my_param}",
                arguments = listOf(
                    navArgument("my_param") {
                        type = NavType.StringType
                    }
                )
            ) {
                val param = it.arguments?.getString("my_param") ?: ""
                AddExerciseView (
                    onNextButtonClicked = {
                        navController.navigate("Day")
                    },
                    param = param
                )
            }
            composable(route = "ExerciseDetail/{my_param}/{second_param}",
                arguments = listOf(
                    navArgument("my_param") {
                        type = NavType.StringType
                    },
                    navArgument("second_param") {
                        type = NavType.StringType
                    }
                )
            ) {
                val exerciseElement = it.arguments?.getString("my_param") ?: ""
                val date = it.arguments?.getString("second_param") ?: ""

                ExerciseDetailView (
                    exerciseElement = exerciseElement,
                    date = date
                )
            }
        }
    }
}


