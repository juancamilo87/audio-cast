package com.scythe.audiocast.api

import com.scythe.audiocast.model.DevicesResponse
import com.scythe.audiocast.model.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //TODO: Add tests? Check empty response works

    @GET("discover/chromecasts")
    suspend fun getDevices(): DevicesResponse

    @GET("discover/media")
    suspend fun getMediaInputs(): MediaResponse

    @GET("/player/cast/{media}/{cast_ip}/{cast_port}")
    suspend fun cast(
        @Path(value = "media") media: String,
        @Path(value = "cast_ip") deviceIp: String,
        @Path(value = "cast_port") devicePort: String
    )

    @GET("/player/stop")
    suspend fun stop()
}
