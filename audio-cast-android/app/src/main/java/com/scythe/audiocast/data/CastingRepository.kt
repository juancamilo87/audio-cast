package com.scythe.audiocast.data

import android.content.Context
import com.google.gson.Gson
import com.scythe.audiocast.model.Device
import com.scythe.audiocast.model.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CastingRepository @Inject constructor(@ApplicationContext context: Context) {

    private companion object {
        const val PREFERENCES = "PREFERENCES"
        const val MEDIA = "media"
        const val CAST_DEVICE = "cast_device"
    }

    private val preferences by lazy {
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setMedia(media: Media) {
        preferences.edit()
            .putString(MEDIA, media.name)
            .apply()
    }

    fun getMedia(): Media? {
        val mediaName = preferences.getString(MEDIA, null)

        return if (mediaName.isNullOrBlank()) {
            null
        } else {
            Media(mediaName)
        }
    }

    fun setCastDevice(device: Device) {
        val json = Gson().toJson(device)
        preferences.edit()
            .putString(CAST_DEVICE, json)
            .apply()
    }

    fun getCastDevice(): Device? {
        val json = preferences.getString(CAST_DEVICE, null)
        return Gson().fromJson(json, Device::class.java)
    }
}