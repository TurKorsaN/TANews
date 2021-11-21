package com.alpersevindik.tanews.data.model.network

import com.google.gson.annotations.SerializedName

data class TopStories(
    val status: String,
    val section: String,
    val last_updated: String,
    val results: List<TopStoriesArticle>
)

data class TopStoriesArticle(
    @SerializedName("uri")
    val id: String,
    val title: String,
    val abstract: String,
    val url: String,
    @SerializedName("short_url")
    val shortUrl: String?,
    val multimedia: List<Media>?,
    @SerializedName("published_date")
    val date: String
)