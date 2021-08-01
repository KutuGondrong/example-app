package com.kutugondrong.exampleapp.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.exampleapp.network.helper.Resource
import kotlinx.coroutines.launch

/**
 * KG KutuGondrong
 * MainViewModel is view model from MainActivity
 * For repository MainViewModel using MainRepository
 * @see MainRepository
 * For register MainViewModel to AppContainer
 * @see MainViewModelFactory
 *
 *
 * @see MainActivity
 */
open class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private var _photos = MutableLiveData<Resource<List<PhotoResponse>>>()
    private var _collection = MutableLiveData<Resource<List<PhotoResponse>>>()
    val photos: LiveData<Resource<List<PhotoResponse>>>
        get() = _photos

    fun getPhotos() {
        _photos.postValue(Resource.loading(ArrayList()))
        viewModelScope.launch {
            repository.getPhotos()?.also {
                if (it.success) {
                    _photos.postValue(Resource.success(it.data))
                    _collection.postValue(Resource.success(it.data))
                } else {
                    _photos.postValue(Resource.error("${it.message}"))
                }
            }
        }
    }

    fun queryData(query: String){
        _photos.value?.status?.also {
            viewModelScope.launch {
                if (query.isEmpty()) {
                    _photos.postValue(_collection.value)
                } else {
                    val data =  _collection.value?.data?.filter {
                        it.description.lowercase().contains(query.lowercase()) ||
                                it.user.name.lowercase().contains(query.lowercase())
                    }
                    data?.apply {
                        _photos.postValue(Resource.success(this))
                    }
                }
            }
        }
    }
}