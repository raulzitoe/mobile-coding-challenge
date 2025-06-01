package com.example.openlanephotogrid.ui.model

/**
 * Represents a photo item in the UI.
 *
 * @property id The unique identifier of the photo.
 * @property thumbnailUrl The URL for the thumbnail version of the photo.
 * @property fullImageUrl The URL for the full-resolution version of the photo.
 * @property width The width of the photo in pixels.
 * @property height The height of the photo in pixels.
 * @property user The name or identifier of the user who uploaded the photo.
 * @property description A description or caption for the photo.
 */
data class PhotoUIModel(
    val id: String,
    val thumbnailUrl: String,
    val fullImageUrl: String,
    val width: Int,
    val height: Int,
    val user: String,
    val description: String
)
