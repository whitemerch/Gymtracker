package com.tc.hamie.chakib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import com.tc.hamie.chakib.ui.theme.GymtrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.appDistribution.showFeedbackNotification(
            // Text providing notice to your testers about collection and
            // processing of their feedback data
            "Send Feedback To Firebase",
            // The level of interruption for the notification
            InterruptionLevel.HIGH)
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

