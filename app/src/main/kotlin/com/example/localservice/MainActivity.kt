package com.example.localservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.localservice.ui.ManeKelsaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var darkTheme by rememberSaveable {
                mutableStateOf(false)
            }

            var language by rememberSaveable {
                mutableStateOf("en")
            }

            ManeKelsaTheme(
                darkTheme = darkTheme
            ) {

                ManeKelsaApp(
                    darkTheme = darkTheme,
                    onThemeChange = {
                        darkTheme = it
                    },
                    language = language,
                    onLanguageChange = {
                        language = it
                    }
                )
            }
        }
    }
}