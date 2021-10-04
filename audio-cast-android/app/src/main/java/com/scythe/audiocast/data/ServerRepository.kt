package com.scythe.audiocast.data

import android.content.Context
import android.net.InetAddresses
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ServerRepository @Inject constructor(@ApplicationContext context: Context) {

    private companion object {
        const val PREFERENCES = "PREFERENCES"
        const val IP_ADDRESS_KEY = "ip_address"
        const val PORT_KEY = "port"
    }

    private val preferences by lazy {
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getIp(): String {
        return preferences.getString(IP_ADDRESS_KEY, "") ?: ""
    }

    fun getPort(): Int {
        return preferences.getInt(PORT_KEY, -1)
    }

    fun getPortText(): String {
        return getPort().run {
            if (this <= 0) {
                ""
            } else this.toString()
        }
    }

    fun getBaseUrl() = "http://${getIp()}:${getPortText()}"

    fun storeServer(ipAddress: String?, port: String?): Boolean {
        val portNum = port?.toIntOrNull()
        if (InetAddresses.isNumericAddress(ipAddress ?: "") &&
            portNum != null &&
            portNum > 0 &&
            portNum < 65536
        ) {
            preferences.edit()
                .putString(IP_ADDRESS_KEY, ipAddress)
                .putInt(PORT_KEY, portNum)
                .apply()
            return true
        }
        return false
    }
}