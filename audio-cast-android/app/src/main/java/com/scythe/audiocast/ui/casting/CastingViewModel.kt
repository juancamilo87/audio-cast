package com.scythe.audiocast.ui.casting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scythe.audiocast.api.ApiService
import com.scythe.audiocast.data.CastingRepository
import com.scythe.audiocast.model.Device
import com.scythe.audiocast.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastingViewModel @Inject constructor(
    private val castingRepository: CastingRepository,
    private val apiService: ApiService
) : ViewModel() {

    val media: Media? by lazy {
        castingRepository.getMedia()
    }

    val castDevice: Device? by lazy {
        castingRepository.getCastDevice()
    }

    fun cast() {
        if (media == null || castDevice == null) return
        viewModelScope.launch(Dispatchers.IO) {
            apiService.cast(media!!.name, castDevice!!.ip, castDevice!!.port.toString())
        }
    }

    fun stop() {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.stop()
        }
    }
}