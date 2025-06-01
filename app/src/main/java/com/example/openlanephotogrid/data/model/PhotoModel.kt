package com.example.openlanephotogrid.data.model

import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.google.gson.annotations.SerializedName

data class PhotoModel(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String? = null,
    @SerializedName("blur_hash")
    val blurHash: String? = null,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val description: String?,
    val user: User,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Collection>,
    val urls: Urls,
    val links: PhotoLinks
) {
    fun toPhotoUIModel(): PhotoUIModel {
        return PhotoUIModel(
            id = id,
            thumbnailUrl = urls.thumb,
            fullImageUrl = urls.small,
            width = width,
            height = height,
            user = user.name,
            description = description.orEmpty()
        )
    }
}

data class User(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    val links: UserLinks
)

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String
)

data class Collection(
    val id: Int,
    val title: String,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("last_collected_at")
    val lastCollectedAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("cover_photo")
    val coverPhoto: PhotoModel?,
    val user: User?
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class PhotoLinks(
    val self: String,
    val html: String,
    val download: String,
    @SerializedName("download_location")
    val downloadLocation: String
)
