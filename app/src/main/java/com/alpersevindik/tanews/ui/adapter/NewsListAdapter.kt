package com.alpersevindik.tanews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alpersevindik.tanews.R
import com.alpersevindik.tanews.data.model.Article
import com.alpersevindik.tanews.utils.loadUrl

class NewsListAdapter(private val itemClickListener: (article: Article) -> Unit) : ListAdapter<Article, NewsListAdapter.ViewHolder>(DIFF_UTIL) {

    var isListLayout = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClickListener(item) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isListLayout) R.layout.news_list_item else R.layout.news_grid_item
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView by lazy { itemView.findViewById<ImageView>(R.id.imageView) }
        private val title by lazy { itemView.findViewById<TextView>(R.id.title) }

        fun bind(article: Article) {
            title.text = article.title
            val url = if (isListLayout) article.imageUrl else article.squareImageUrl
            imageView.loadUrl(url)
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}