package com.scythe.audiocast.api

import com.scythe.audiocast.model.DevicesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("discover/chromecasts")
    suspend fun getDevices(): DevicesResponse
    //TODO: Add tests? Check empty response works
}