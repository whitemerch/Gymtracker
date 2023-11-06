package com.tc.hamie.chakib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tc.hamie.chakib.ui.theme.GymtrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymtrackerTheme {
                MyApp()
            }
        }
    }
}

@Preview()
@Composable
fun Preview(){
    GymtrackerTheme {
        MyApp()
    }
}

