package com.alpersevindik.tanews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alpersevindik.tanews.data.local.dao.ArticleDao
import com.alpersevindik.tanews.data.model.Article

@Database(entities = [Article::class], version = 1)
abstract class Database: RoomDatabase() {

    companion object {
        const val DB_NAME = "TANews"
    }

    abstract fun articleDao(): ArticleDao
}