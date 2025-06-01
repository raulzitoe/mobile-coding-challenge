package com.example.openlanephotogrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.openlanephotogrid.ui.gridscreen.PhotoGridScreen
import com.example.openlanephotogrid.ui.theme.OPENLANEPhotoGridTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OPENLANEPhotoGridTheme {
                Scaffold { innerPadding ->
                    PhotoGridScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
