package com.alpersevindik.tanews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alpersevindik.tanews.data.Repository
import com.alpersevindik.tanews.data.model.Article
import com.alpersevindik.tanews.utils.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _newsLiveData = MutableLiveData<List<Article>>(emptyList())
    val newsLiveData: LiveData<List<Article>>
        get() = _newsLiveData

    fun loadWorldTopStories() {
        repository.getWorldTopStories()
            .subscribeOn(Schedulers.io())
            .subscribe({ _newsLiveData.postValue(it) },
                       { ex -> _newsLiveData.postValue(emptyList()) })
            .addTo(compositeDisposable)
    }

    fun search(query: CharSequence?) {
        if (query.isNullOrBlank()) return
        repository.search(query.toString())
            .subscribeOn(Schedulers.io())
            .subscribe({ _newsLiveData.postValue(it) },
                       { ex ->
                           ex.printStackTrace()
                           _newsLiveData.postValue(emptyList())
                       })
            .addTo(compositeDisposable)
    }
}