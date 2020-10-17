package com.epitech.epicture.model

import com.google.gson.annotations.SerializedName

/**
 * Fetched DAO representing an image / an album when making HTTP requests to imgur
 */
data class ImgurImage(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("is_album") val isAlbum: Boolean,
        @SerializedName("link") val link: String,
        @SerializedName("ups") val ups: Int,
        @SerializedName("downs") val downs: Int,
        @SerializedName("cover") val cover: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("vote") val vote: String?,
        @SerializedName("comment_count") val commentCount: Int,
        @SerializedName("favorite_count") val favoriteCount: Int,
        @SerializedName("favorite") val isFavorite: Boolean,
        @SerializedName("images") val images: List<Image>?
)