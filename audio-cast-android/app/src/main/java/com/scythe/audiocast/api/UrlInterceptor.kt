package com.scythe.audiocast.api

import com.scythe.audiocast.data.ServerRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UrlInterceptor @Inject constructor(
    private val serverRepository: ServerRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        val host = serverRepository.getIp()
        val port = serverRepository.getPort()

        val newUrl = request.url.newBuilder()
            .host(host)
            .port(port)
            .build()

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}