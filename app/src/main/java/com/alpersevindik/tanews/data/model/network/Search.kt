package com.alpersevindik.tanews.data.model.network

import com.google.gson.annotations.SerializedName

data class Search(
    val status: String,
    val response: SearchResponse
)

data class SearchResponse(
    val docs: List<SearchArticle>
)

data class SearchArticle(
    @SerializedName("_id")
    val id: String,
    val abstract: String,
    @SerializedName("lead_paragraph")
    val leadParagraph: String?,
    @SerializedName("web_url")
    val url: String?,
    val headline: Headline,
    val multimedia: List<Media>?,
    @SerializedName("pub_date")
    val publishDate: String
)

data class Headline(
    val main: String
)