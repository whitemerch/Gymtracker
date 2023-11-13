package com.tc.hamie.chakib

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.collections.Set


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ExerciseDetailView(
    exerciseElement: String,
    date: String,
    viewModel: ExerciseDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val gson = Gson()
    var exerciseObject by remember { mutableStateOf(gson.fromJson(exerciseElement, ExerciseElement::class.java)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ExerciseCard(exerciseObject)
        SetsItems(exerciseObject.sets)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                showBottomSheet = true
            },
            containerColor = Color(0xff75d4bf),
            contentColor = Color.White,
            icon = {
                androidx.compose.material3.Icon(
                    Icons.Filled.Add,
                    "Extended floating action button."
                )
            },
            text = { Text(text = "Add Reps") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
        )
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Weight Input
                    var weight by remember { mutableIntStateOf(0) }
                    OutlinedTextField(
                        value = weight.toString(),
                        onValueChange = { weight = it.toIntOrNull() ?: 0 },
                        label = { Text("Weight") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Repetitions Input
                    var repetitions by remember { mutableIntStateOf(0) }
                    OutlinedTextField(
                        value = repetitions.toString(),
                        onValueChange = { repetitions = it.toIntOrNull() ?: 0 },
                        label = { Text("Repetitions") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) {
                            Text("Cancel")
                        }
                        Button(onClick = {
                            // Use 'weight' and 'repetitions' as needed
                            scope.launch {
                                sheetState.hide()
                                exerciseObject =
                                    viewModel.addReps(exerciseObject, weight, repetitions, date)
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ExerciseCard(exerciseElement:ExerciseElement) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = exerciseElement.profileImage
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = exerciseElement.exercise,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SetsItems(sets: List<Setstructure>) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(500.dp)
    ) {
        items(sets) { set ->
            SetItem(set = set)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetItem(set: Setstructure) {
    Card(
        onClick = {

        },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "${set.repetitions} reps", fontWeight = FontWeight.Bold)
            Text(text = "Weight: ${set.weight} kg", fontWeight = FontWeight.Normal)
        }
    }
}
