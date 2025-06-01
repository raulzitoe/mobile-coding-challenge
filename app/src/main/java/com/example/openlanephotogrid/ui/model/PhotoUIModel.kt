package com.example.openlanephotogrid.ui.model

data class PhotoUIModel(
    val id: String,
    val thumbnailUrl: String,
    val fullImageUrl: String,
    val width: Int,
    val height: Int,
    val user: String,
    val description: String
)
