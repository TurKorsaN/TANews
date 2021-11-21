package com.alpersevindik.tanews.data.network

import com.alpersevindik.tanews.data.model.network.Search
import com.alpersevindik.tanews.data.model.network.TopStories
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTService {

    @GET(Constants.URL_SEARCH)
    fun search(@Query(Constants.PARAM_QUERY) query: String, @Query(Constants.PARAM_API_KEY) apiKey: String): Single<Search>

    @GET(Constants.URL_TOP_STORIES_WORLD)
    fun getWorldTopStories(@Query(Constants.PARAM_API_KEY) apiKey: String): Single<TopStories>
}