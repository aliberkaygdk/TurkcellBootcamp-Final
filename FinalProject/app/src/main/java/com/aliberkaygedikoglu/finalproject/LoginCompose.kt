package com.aliberkaygedikoglu.finalproject

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class LoginCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login()

        }


    }
}




