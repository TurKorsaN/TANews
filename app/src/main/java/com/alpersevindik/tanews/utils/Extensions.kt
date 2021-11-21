package com.alpersevindik.tanews.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.alpersevindik.tanews.R
import com.alpersevindik.tanews.data.model.Article
import com.alpersevindik.tanews.data.model.network.SearchArticle
import com.alpersevindik.tanews.data.model.network.TopStoriesArticle
import com.alpersevindik.tanews.data.network.Constants
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Disposable.addTo(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)

fun SearchArticle.toArticle() = Article(
    id = id,
    title = headline.main,
    subTitle = abstract,
    imageUrl = multimedia?.find { it.type == "image" && it.subtype == "largeWidescreen1050" }?.url?.let { Constants.IMAGE_BASE_URL + it },
    squareImageUrl = multimedia?.find { it.type == "image" && it.subtype == "square640" }?.url?.let { Constants.IMAGE_BASE_URL + it },
    date = Instant.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").parse(publishDate)).toEpochMilli(),
    content = leadParagraph ?: "",
    shareUrl = url
)

fun TopStoriesArticle.toArticle() = Article(
    id = id,
    title = title,
    subTitle = abstract,
    imageUrl = multimedia?.firstOrNull()?.url,
    squareImageUrl = multimedia?.firstOrNull()?.url,
    date = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(date, Instant::from).toEpochMilli(),
    content = "",
    shareUrl = shortUrl ?: url
)

fun ImageView.loadUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .error(ContextCompat.getDrawable(context, R.drawable.ic_image))
        .into(this)
}

fun Long.dateFormat() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()).toString()