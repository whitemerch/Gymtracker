package com.tc.hamie.chakib

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.SortedMap

@Composable
fun AddExerciseView(onNextButtonClicked: () -> Unit, param: String) {
    var exercises by rememberSaveable { mutableStateOf(ExercisesList().getAllExercises()) }
    ListDisplayed(exercises = exercises, onNextButtonClicked, param)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDisplayed(exercises: SortedMap<String, List<Exercise>>, onNextButtonClicked: () -> Unit, param:String){
    val exercisesCategorized = exercises.map {
        Category(
            name = it.key.toString(),
            items = it.value
        )
    }
    LazyColumn{
        exercisesCategorized.forEach { list ->
        stickyHeader {
            CategoryUI(list.name)
        }
        items(list.items) { exercise ->
            ItemUI(exercise, onNextButtonClicked, param)
        }
    }
    }
}

@Composable
private fun CategoryUI(text: String) {
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
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemUI(exercise: Exercise,
                   onNextButtonClicked: () -> Unit,
                   param: String,
                   viewModel: AddExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Card(
        onClick = {
            coroutineScope.launch {
            viewModel.saveExercise(param, exercise.exerciseName)
            onNextButtonClicked()
        }
                  },
       elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
    {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(
                id = exercise.profileImage
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
            Text(text = exercise.exerciseName,
                fontWeight = FontWeight.Bold
            )
        }
    }
    }
}


