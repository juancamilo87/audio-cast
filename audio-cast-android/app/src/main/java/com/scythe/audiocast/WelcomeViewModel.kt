package com.scythe.audiocast

import android.app.Application
import android.content.Context
import android.net.InetAddresses
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {

    private companion object {
        const val PREFERENCES = "PREFERENCES"
        const val IP_ADDRESS_KEY = "ip_address"
        const val PORT_KEY = "port"
    }

    //TODO: Add DI to remove AndroidViewModel and inject shared preferences, or add repository for
    // this abstracting shared preferences completely
    private val preferences by lazy {
        application.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    private val _serverIpAddress = MutableLiveData(preferences.getString(IP_ADDRESS_KEY, "") ?: "")
    val serverIpAddress: LiveData<String> = _serverIpAddress

    private val _serverPort = MutableLiveData(preferences.getInt(PORT_KEY, -1).run {
        if (this == -1) {
            ""
        } else this.toString()
    })
    val serverPort: LiveData<String> = _serverPort

    fun onServerChange(newServer: String) {
        val (ip, port) = newServer.split(":")
        _serverIpAddress.value = ip
        _serverPort.value = port
    }

    fun saveServer() {
        val ip = serverIpAddress.value ?: ""
        val port = serverPort.value?.toIntOrNull()
        if (InetAddresses.isNumericAddress(ip) && port != null && port > 0 && port < 65536) {
            preferences.edit().run {
                putString(IP_ADDRESS_KEY, ip)
                putInt(PORT_KEY, port)
                apply()
            }

        }
    }
}