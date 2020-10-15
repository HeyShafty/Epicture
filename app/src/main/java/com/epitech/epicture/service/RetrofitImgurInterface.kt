package com.epitech.epicture.service

import com.epitech.epicture.config.Config
import com.epitech.epicture.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RetrofitImgurService {
    @FormUrlEncoded
    @POST("/oauth2/token")
    suspend fun getNewAccessToken(
            @Field(Config.REFRESH_TOKEN_KEY) refreshToken: String,
            @Field(Config.CLIENT_ID_KEY) clientId: String,
            @Field(Config.CLIENT_SECRET_KEY) clientSecret: String,
            @Field(Config.GRANT_TYPE_KEY) grantType: String
    ): ImgurCredentials

    @GET("3/account/{username}/favorites/{page}/{favoritesSort}")
    suspend fun getUserFavorites(
            @Header("Authorization") accessToken: String,
            @Path("username") username: String,
            @Path("page") page: Int,
            @Path("favoritesSort") favoritesSort: String,
    ): BasicDataResponse<ImgurImage>

    @GET("3/gallery/album/{albumId}")
    suspend fun getAlbum(
            @Header("Authorization") clientId: String,
            @Path("albumId") albumId: String
    ): BasicDataResponse<ImgurImage>

    @GET("3/gallery/image/{imageId}")
    suspend fun getImage(
            @Header("Authorization") clientId: String,
            @Path("imageId") imageId: String
    ): BasicDataResponse<ImgurImage>

    @POST("3/album/{albumId}/favorite")
    suspend fun favAlbum(
            @Header("Authorization") accessToken: String,
            @Path("albumId") albumId: String
    )

    @POST("3/image/{imageId}/favorite")
    suspend fun favImage(
            @Header("Authorization") accessToken: String,
            @Path("imageId") imageId: String
    )

    @GET("3/account/me/images/{page}")
    suspend fun getAccountImages(
            @Header("Authorization") accessToken: String,
            @Path("page") page: Int,
    ): BasicDataResponse<Image>

    @Multipart
    @POST("3/upload")
    suspend fun uploadImage(
            @Header("Authorization") accessToken: String,
            @Part image: MultipartBody.Part,
            @Part("title") title: RequestBody,
            @Part("description") description: RequestBody,
            @Part("type") type: RequestBody,
    )

    @POST("3/gallery/{id}/vote/{vote}")
    suspend fun vote(
            @Header("Authorization") accessToken: String,
            @Path("id") id: String,
            @Path("vote") vote: String
    )

    @GET("3/gallery/{commentId}/comments")
    suspend fun getComments(
            @Header("Authorization") clientId: String,
            @Path("commentId") commentId: String,
    ): BasicDataResponse<Comment>

    @POST("3/comment/{commentId}/vote/{vote}")
    suspend fun voteComment(
            @Header("Authorization") accessToken: String,
            @Path("commentId") commentId: String,
            @Path("vote") vote: String
    )

    @Multipart
    @POST("3/gallery/{id}/comment\n")
    suspend fun createComment(
            @Header("Authorization") accessToken: String,
            @Path("id") id: RequestBody,
            @Part("comment") comment: RequestBody
    )
}