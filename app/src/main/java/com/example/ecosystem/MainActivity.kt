package com.example.ecosystem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.ecosystem.ui.navigation.AppNavigation
import com.example.ecosystem.ui.theme.EcoSystemTheme
import com.example.ecosystem.ui.viewmodel.TripListViewModel

class MainActivity : ComponentActivity() {
    private lateinit var tripListViewModel: TripListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        tripListViewModel = ViewModelProvider(this)[TripListViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            EcoSystemTheme {
                AppNavigation(viewModel = tripListViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    private companion object {
        const val TAG = "SIS4Activity"
    }
}
