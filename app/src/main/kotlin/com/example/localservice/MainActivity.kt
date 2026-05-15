package com.example.localservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = FirebaseOptions.Builder()
            .setProjectId("gen-lang-client-0507183686")
            .setApplicationId("1:304465623593:web:2abd0f1acbadae5193ba38")
            .setApiKey("AIzaSyDwlnbI_Xd4dv8wTXSeMW6ceGKiSPWHYLM")
            .build()

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this, options)
        }

        setContent {
            ManeKelsaApp()
        }
    }
}
