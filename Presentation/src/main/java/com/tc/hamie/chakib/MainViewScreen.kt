package com.tc.hamie.chakib

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.gson.Gson


@Composable
fun MainViewApp(modifier: Modifier = Modifier,
                addExerciseButton: (String) -> Unit,
                exerciseDetailButton: (String, String) -> Unit)
{
    var currentDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    Surface(color = Color(0xFFFFFFFF)) {
        Box(modifier.fillMaxSize()) {
            Column(
                modifier
                    .fillMaxSize()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.height(200.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        minusClicked = { currentDate = currentDate.minusDays(1) },
                        Icons.Filled.ArrowBack
                    )
                    CircleWithDate(currentDate = currentDate)
                    Icon(
                        minusClicked = { currentDate = currentDate.plusDays(1) },
                        Icons.Filled.ArrowForward
                    )
                }
                val mContext = LocalContext.current
                val mYear = currentDate.year
                val mMonth = currentDate.monthValue - 1
                val mDay = currentDate.dayOfMonth

                val mDatePickerDialog = DatePickerDialog(
                    mContext,
                    { _: DatePicker, year: Int, month: Int, day: Int ->
                        currentDate = LocalDate.of(year, month + 1, day)
                    }, mYear, mMonth, mDay
                )
                ElevatedButton(
                    onClick = { mDatePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(Color(0xff75d4bf)),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Pick a Date", color = Color.White)
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                ExercisesItems(currentDate.format(DateTimeFormatter.ofPattern("MMM dd")), exerciseDetailButton)
            }
            // Add ExtendedFloatingActionButton
            ExtendedFloatingActionButton(
                onClick = {
                    addExerciseButton(currentDate.format(DateTimeFormatter.ofPattern("MMM dd")))
                },
                containerColor = Color(0xff75d4bf),
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                text = { Text(text = "Add exercise") },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}


@Composable
fun CircleWithDate(currentDate: LocalDate) {
    val formattedDate = remember(currentDate) {
        mutableStateOf(currentDate.format(DateTimeFormatter.ofPattern("MMM dd")))
    }

    Box(
        modifier = Modifier.width(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawCircle(
                color = Color(0xff75d4bf),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2,
                style = Stroke(10F)
            )
        }

        Text(
            text = formattedDate.value,
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun Icon(minusClicked: () -> Unit, icon: ImageVector) {
    IconButton(
        onClick = minusClicked
    ) {
        Icon(imageVector = icon, contentDescription = "", tint = Color(0xff75d4bf))
    }
}

@Composable
fun ExercisesItems(
    date: String,
    exerciseDetailButton: (String, String) -> Unit,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var exercisesList by remember { mutableStateOf<List<ExerciseElement>?>(null) }

    LaunchedEffect(date) {
        val fetchedExercises = viewModel.getExercises(date)
        exercisesList = fetchedExercises
    }

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(exercisesList.orEmpty()) { exerciseElement ->
            ExerciseRow(exerciseElement = exerciseElement, exerciseDetailButton, date)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseRow(exerciseElement: ExerciseElement,
                        exerciseDetailButton: (String, String) -> Unit,
                        date: String
) {
    Card(
        onClick = {
            val gson = Gson()
            val exerciseElementString = gson.toJson(exerciseElement)
            exerciseDetailButton(exerciseElementString, date)
        },
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

