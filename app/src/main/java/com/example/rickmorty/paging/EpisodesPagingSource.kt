package com.example.rickmorty.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.remote.CharacterService
import javax.inject.Inject

class EpisodesPagingSource @Inject constructor(
    private val apiService: CharacterService
) : PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {

        val pageNumber = params.key ?: 1

        return try {
            val response = apiService.getAllEpisode(pageNumber)
            val episodeData = response.results

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = episodeData,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        //return null
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}