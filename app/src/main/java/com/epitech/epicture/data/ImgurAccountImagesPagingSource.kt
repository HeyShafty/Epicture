package com.epitech.epicture.data

import androidx.paging.PagingSource
import com.epitech.epicture.HomeActivityData
import com.epitech.epicture.config.Config.Companion.FORMATS_EXTENSION
import com.epitech.epicture.config.Config.Companion.PAGE_INITIAL_IDX
import com.epitech.epicture.model.Image
import com.epitech.epicture.service.ImgurService
import retrofit2.HttpException
import java.io.IOException

class ImgurAccountImagesPagingSource : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: PAGE_INITIAL_IDX
        return try {
            val images =
                ImgurService.getAccountImages(HomeActivityData.imgurCredentials?.accessToken ?: "")
            val imageList = mutableListOf<Image>()

            for (image in images) {
                if (!FORMATS_EXTENSION.containsKey(image.type)) {
                    continue
                }
                imageList.add(
                    Image(
                        image.id,
                        image.title,
                        image.description,
                        "https://i.imgur.com/" + image.id + FORMATS_EXTENSION[image.type],
                        image.ups,
                        image.downs,
                        image.isAlbum,
                        image.type
                    )
                )
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