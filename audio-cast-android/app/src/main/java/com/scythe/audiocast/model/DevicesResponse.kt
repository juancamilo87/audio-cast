package com.scythe.audiocast.model

import com.google.gson.annotations.SerializedName

data class DevicesResponse(
    @SerializedName("devices")
    val deviceResponses: List<DeviceResponse>
) {
    data class DeviceResponse(
        val ip: String,
        val name: String,
        val port: Int,
        val type: String,
        val uuid: String
    )
}