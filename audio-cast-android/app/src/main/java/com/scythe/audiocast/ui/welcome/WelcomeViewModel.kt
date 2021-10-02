package com.scythe.audiocast.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scythe.audiocast.data.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val serverRepository: ServerRepository
) : ViewModel() {

    private val _serverIpAddress = MutableLiveData(serverRepository.getIp())
    val serverIpAddress: LiveData<String> = _serverIpAddress

    private val _serverPort = MutableLiveData(serverRepository.getPort())
    val serverPort: LiveData<String> = _serverPort

    fun onServerChange(newServer: String) {
        val (ip, port) = newServer.split(":")
        _serverIpAddress.value = ip
        _serverPort.value = port
    }

    fun saveServer(): Boolean {
        return serverRepository.storeServer(serverIpAddress.value, serverPort.value)
    }
}