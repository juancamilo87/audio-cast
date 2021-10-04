package com.scythe.audiocast.model

data class Device(
    val ip: String,
    val name: String,
    val port: Int,
    val type: String,
    val uuid: String
)

fun DevicesResponse.DeviceResponse.toDevice(): Device {
    val readableType = convertType(type)
    return Device(ip, name, port, readableType, uuid)
}

private fun convertType(type: String): String {
    return when (type) {
        "Eureka Dongle" -> "Chromecast"
        else -> type
    }
}
