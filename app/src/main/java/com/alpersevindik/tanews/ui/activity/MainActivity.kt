package com.alpersevindik.tanews.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alpersevindik.tanews.R
import com.alpersevindik.tanews.ui.adapter.NewsListAdapter
import com.alpersevindik.tanews.utils.addTo
import com.alpersevindik.tanews.viewmodel.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding4.widget.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    private lateinit var searchView: SearchView
    private val newsList by lazy { findViewById<RecyclerView>(R.id.newsList) }
    private val toggleListStyleButton by lazy { findViewById<FloatingActionButton>(R.id.toggleListStyleButton) }

    private val linearLayoutManager by lazy { LinearLayoutManager(this, RecyclerView.VERTICAL, false) }
    private val gridLayoutManager by lazy { GridLayoutManager(this, 2, RecyclerView.VERTICAL, false) }
    private val newsAdapter by lazy {
        NewsListAdapter { article ->
            NewsDetailActivity.start(this, article)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        initSearchView()
        return true
    }

    private fun initSearchView() {
        searchView.maxWidth = Integer.MAX_VALUE;
        searchView.queryTextChanges()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({ newsViewModel.search(it) },
                       { ex -> ex.printStackTrace() })
            .addTo(compositeDisposable)

        searchView.setOnCloseListener {
            newsViewModel.loadWorldTopStories()
            false
        }
    }

    private fun initViews() {
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.news_item_divider)!!
        newsList.layoutManager = linearLayoutManager
        newsList.adapter = newsAdapter
        newsList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL).apply { setDrawable(dividerDrawable) })
        newsList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply { setDrawable(dividerDrawable) })
        toggleListStyleButton.setOnClickListener { toggleListStyle() }
    }

    private fun initData() {
        newsViewModel.newsLiveData.observe(this) {
            newsAdapter.submitList(it)
        }

        newsViewModel.loadWorldTopStories()
    }

    private fun toggleListStyle() {
        newsList.layoutManager = if (newsList.layoutManager is GridLayoutManager) {
            newsAdapter.isListLayout = true
            toggleListStyleButton.setImageResource(R.drawable.ic_grid)
            linearLayoutManager
        } else {
            newsAdapter.isListLayout = false
            toggleListStyleButton.setImageResource(R.drawable.ic_list)
            gridLayoutManager
        }
    }
}