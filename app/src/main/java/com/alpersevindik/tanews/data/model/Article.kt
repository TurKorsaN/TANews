package com.alpersevindik.tanews.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Article(
    @PrimaryKey
    val id: String,
    val title: String,
    val subTitle: String,
    val imageUrl: String?,
    val squareImageUrl: String?,
    val date: Long,
    val content: String,
    val shareUrl: String?
) : Parcelable
