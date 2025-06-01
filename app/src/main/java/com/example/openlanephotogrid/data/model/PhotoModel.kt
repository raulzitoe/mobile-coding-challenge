package com.example.openlanephotogrid.data.model

import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.google.gson.annotations.SerializedName

/**
 * Represents a photo object retrieved from the Unsplash API.
 *
 * @property id The unique identifier of the photo.
 * @property createdAt The timestamp when the photo was created.
 * @property updatedAt The timestamp when the photo was last updated.
 * @property width The width of the photo in pixels.
 * @property height The height of the photo in pixels.
 * @property color The dominant color of the photo.
 * @property blurHash A string representing a blurred version of the photo, useful for placeholders.
 * @property likes The number of likes the photo has received.
 * @property likedByUser Indicates whether the current user has liked the photo.
 * @property description A description of the photo.
 * @property user The user who uploaded the photo.
 * @property currentUserCollections A list of collections the photo belongs to for the current user.
 * @property urls Contains various URLs for different sizes of the photo.
 * @property links Contains links related to the photo, such as download links.
 */
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

/**
 * Represents a user.
 *
 * @property id The user's ID.
 * @property username The user's username.
 * @property name The user's name.
 * @property portfolioUrl The URL of the user's portfolio.
 * @property bio The user's biography.
 * @property location The user's location.
 * @property totalLikes The total number of likes the user has received.
 * @property totalPhotos The total number of photos the user has uploaded.
 * @property totalCollections The total number of collections the user has created.
 * @property instagramUsername The user's Instagram username.
 * @property twitterUsername The user's Twitter username.
 * @property profileImage The user's profile image.
 * @property links Links related to the user.
 */
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

/**
 * Data class representing the profile image URLs for a user.
 *
 * @property small The URL for the small-sized profile image.
 * @property medium The URL for the medium-sized profile image.
 * @property large The URL for the large-sized profile image.
 */
data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

/**
 * Data class representing links related to a user.
 *
 * @property self The URL for the user's profile.
 * @property html The URL for the user's HTML profile.
 * @property photos The URL for the user's photos.
 * @property likes The URL for the user's liked photos.
 * @property portfolio The URL for the user's portfolio.
 */
data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String
)

/**
 * Represents a collection of photos.
 *
 * @property id The unique identifier of the collection.
 * @property title The title of the collection.
 * @property publishedAt The date and time when the collection was published, in ISO 8601 format.
 * @property lastCollectedAt The date and time when the collection was last collected, in ISO 8601 format.
 * @property updatedAt The date and time when the collection was last updated, in ISO 8601 format.
 * @property coverPhoto The cover photo of the collection.
 * @property user The user who created the collection.
 */
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

/**
 * Data class representing the URLs for different image sizes of a photo.
 *
 * @property raw The URL for the raw, unprocessed image.
 * @property full The URL for the full-resolution image.
 * @property regular The URL for a regular-sized image (typically around 1080px wide).
 * @property small The URL for a small-sized image (typically around 400px wide).
 * @property thumb The URL for a thumbnail-sized image (typically around 200px wide).
 */
data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

/**
 * Data class representing the links associated with a photo.
 *
 * @property self The link to the photo itself.
 * @property html The link to the photo's HTML page.
 * @property download The link to download the photo.
 * @property downloadLocation The link to the download location of the photo.
 */
data class PhotoLinks(
    val self: String,
    val html: String,
    val download: String,
    @SerializedName("download_location")
    val downloadLocation: String
)
