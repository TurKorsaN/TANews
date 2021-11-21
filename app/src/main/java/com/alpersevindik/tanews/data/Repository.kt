package com.alpersevindik.tanews.data

import com.alpersevindik.tanews.data.local.Database
import com.alpersevindik.tanews.data.model.Article
import com.alpersevindik.tanews.data.network.Constants
import com.alpersevindik.tanews.data.network.NYTService
import com.alpersevindik.tanews.utils.toArticle
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val nytService: NYTService,
    private val database: Database
) {

    fun search(query: String): @NonNull Single<List<Article>> {
        return nytService.search(query, Constants.API_KEY)
            .map { it.response.docs.map { searchArticle -> searchArticle.toArticle() } }
    }

    fun getWorldTopStories(): @NonNull Single<List<Article>> {
        val articleDao = database.articleDao()
        return nytService.getWorldTopStories(Constants.API_KEY)
            .map { it.results.map { topStory -> topStory.toArticle() } }
            .doOnSuccess {
                articleDao.deleteAll()
                articleDao.insertAll(it)
            }
            .doOnError { it.printStackTrace() }
            .onErrorReturn { articleDao.getAll() }
    }
}