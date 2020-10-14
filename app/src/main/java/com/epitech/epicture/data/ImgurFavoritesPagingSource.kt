package com.epitech.epicture.data

import androidx.paging.PagingSource
import com.epitech.epicture.HomeActivityData
import com.epitech.epicture.model.Image
import com.epitech.epicture.service.ImgurService
import retrofit2.HttpException
import java.io.IOException

class ImgurFavoritesPagingSource : PagingSource<Int, Image>() {
    private val PAGE_INITIAL_IDX = 0
    private val FORMATS_EXTENSION = mapOf(
        "image/jpeg" to ".jpg",
        "image/gif" to ".gif",
        "image/png" to ".png"
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: PAGE_INITIAL_IDX
        return try {
            val favoriteObjects = ImgurService.getUserFavorites(
                HomeActivityData.imgurCredentials?.accessToken ?: "",
                HomeActivityData.imgurCredentials?.accountUsername ?: "",
                position,
                "newest"
            )
            val imageList = mutableListOf<Image>()

            for (favoriteObject in favoriteObjects) {
                if (!FORMATS_EXTENSION.containsKey(favoriteObject.type)) {
                    continue
                }
                if (favoriteObject.isAlbum) {
                    imageList.add(
                        Image(
                            favoriteObject.id,
                            favoriteObject.title,
                            favoriteObject.description,
                            "https://imgur.com/gallery/" + favoriteObject.cover + FORMATS_EXTENSION[favoriteObject.type],
                            favoriteObject.ups,
                            favoriteObject.downs,
                            favoriteObject.isAlbum
                        )
                    )
                } else {
                    imageList.add(
                        Image(
                            favoriteObject.id,
                            favoriteObject.title,
                            favoriteObject.description,
                            favoriteObject.link + favoriteObject.id + FORMATS_EXTENSION[favoriteObject.type],
                            favoriteObject.ups,
                            favoriteObject.downs,
                            favoriteObject.isAlbum
                        )
                    )
                }
            }
            LoadResult.Page(
                data = imageList,
                prevKey = if (position == PAGE_INITIAL_IDX) null else position - 1,
                nextKey = if (imageList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}