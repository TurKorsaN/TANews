package com.alpersevindik.tanews.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ShareCompat
import com.alpersevindik.tanews.R
import com.alpersevindik.tanews.data.model.Article
import com.alpersevindik.tanews.utils.dateFormat
import com.alpersevindik.tanews.utils.loadUrl

class NewsDetailActivity : BaseActivity() {

    companion object {
        private const val EXTRA_ARTICLE = "EXTRA_ARTICLE"

        @JvmStatic
        fun start(context: Context, article: Article) {
            val starter = Intent(context, NewsDetailActivity::class.java)
                .putExtra(EXTRA_ARTICLE, article)
            context.startActivity(starter)
        }
    }

    private val article: Article by lazy { intent.getParcelableExtra(EXTRA_ARTICLE)!! }

    private val imageView by lazy { findViewById<ImageView>(R.id.imageView) }
    private val title by lazy { findViewById<TextView>(R.id.title) }
    private val subtitle by lazy { findViewById<TextView>(R.id.subtitle) }
    private val date by lazy { findViewById<TextView>(R.id.date) }
    private val content by lazy { findViewById<TextView>(R.id.content) }
    private val share by lazy { findViewById<View>(R.id.share) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageView.loadUrl(article.imageUrl)
        title.text = article.title
        subtitle.text = article.subTitle
        date.text = article.date.dateFormat()
        content.text = article.content
        share.setOnClickListener { share() }
    }

    private fun share() {
        ShareCompat.IntentBuilder(this)
            .setSubject(article.title)
            .setText(article.shareUrl)
            .setType("application/text")
            .startChooser()
    }
}