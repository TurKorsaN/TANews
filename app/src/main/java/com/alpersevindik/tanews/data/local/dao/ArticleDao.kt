package com.alpersevindik.tanews.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alpersevindik.tanews.data.model.Article

@Dao
interface ArticleDao {

    @Insert
    fun insert(article: Article)

    @Insert
    fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getArticle(id: String): Article

    @Query("DELETE FROM article WHERE id = :articleId")
    fun delete(articleId: String)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM article")
    fun deleteAll()
}