package com.example.openlanephotogrid.ui.gridscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PhotoGridScreen(modifier: Modifier = Modifier) {
    val viewModel: PhotoGridViewModel = hiltViewModel()
}
