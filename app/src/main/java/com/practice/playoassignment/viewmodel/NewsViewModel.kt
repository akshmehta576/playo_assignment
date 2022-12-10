package com.practice.playoassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.playoassignment.model.NewsResponse
import com.practice.playoassignment.repository.NewsRepository
import com.practice.playoassignment.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _listofNewsState = MutableStateFlow<NewsListState>(NewsListState.Empty)
    val listofNewsState: StateFlow<NewsListState> = _listofNewsState.asStateFlow()

    sealed class NewsListState {
        object Loading : NewsListState()
        data class Success(
            val data: NewsResponse
        ) : NewsListState()

        data class Error(val message: String) : NewsListState()
        object Empty : NewsListState()
    }

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response = repository.getNews()) {
                    is Resources.Success -> {
                        if (response.data != null) {
                            _listofNewsState.value = NewsListState.Success(response.data)
                        }
                    }
                    is Resources.Error -> {
                        _listofNewsState.value = NewsListState.Error(response.message.toString())
                    }
                    is Resources.Loading -> {
                        _listofNewsState.value = NewsListState.Loading
                    }
                    else -> {

                    }
                }
            } catch (e: Exception) {
                _listofNewsState.value = NewsListState.Error(e.message.toString())
            }
        }
    }
}