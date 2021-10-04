package com.scythe.audiocast.ui.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scythe.audiocast.api.ApiService
import com.scythe.audiocast.model.Device
import com.scythe.audiocast.model.DevicesResponse
import com.scythe.audiocast.model.toDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _devices = MutableLiveData<List<Device>>(emptyList())
    val devices: LiveData<List<Device>> = _devices

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            val newDevices = getDevices()
                .map { it.toDevice() }
                .sortedBy { it.name }
            _devices.value = newDevices
        }
    }

    fun selectDevice(device: Device) {
        //TODO: Implement
    }

    private suspend fun getDevices(): List<DevicesResponse.DeviceResponse> {
        _isRefreshing.emit(true)
        val devices = withContext(Dispatchers.IO) {
            apiService.getDevices().deviceResponses
        }
        _isRefreshing.emit(false)
        return devices
    }
}