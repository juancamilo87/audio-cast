package com.scythe.audiocast.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scythe.audiocast.api.ApiService
import com.scythe.audiocast.data.CastingRepository
import com.scythe.audiocast.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val apiService: ApiService,
    private val castingRepository: CastingRepository
) : ViewModel() {
    private val _mediaInputs = MutableLiveData<List<Media>>(emptyList())
    val mediaInputs: LiveData<List<Media>> = _mediaInputs

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            _mediaInputs.value = getInputs()
                .map { Media(it) }
                .sortedBy { it.name }
        }
    }

    fun selectMedia(media: Media) {
        castingRepository.setMedia(media)
    }

    private suspend fun getInputs(): List<String> {
        _isRefreshing.emit(true)
        val inputs = withContext(Dispatchers.IO) {
            apiService.getMediaInputs().inputs
        }
        _isRefreshing.emit(false)
        return inputs
    }
}