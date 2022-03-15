package com.mlodi.sicredi.devandroidtest.data.repository

import android.util.Log
import com.mlodi.sicredi.devandroidtest.data.api.model.CheckIn
import com.mlodi.sicredi.devandroidtest.data.api.model.Event
import com.mlodi.sicredi.devandroidtest.data.api.service.SicrediService
import java.lang.Exception

object SicrediRepository {

    suspend fun getEvents(onSuccess: ((eventList: List<Event>?) -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        try {
            val response = SicrediService.service.getEvents()
            if (response.isSuccessful) {
                onSuccess?.invoke(response.body())
            } else {
                onFailure?.invoke()
            }
        } catch (e: Exception) {
            onFailure?.invoke()
        }
    }

    suspend fun getEvent(id: String, onSuccess: ((eventList: Event?) -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        try {
            val response = SicrediService.service.getEvent(id)
            if (response.isSuccessful) {
                onSuccess?.invoke(response.body())
            } else {
                onFailure?.invoke()
            }
        } catch (e: Exception) {
            Log.d("Test", "${e.message}")
            onFailure?.invoke()
        }
    }

    suspend fun doCheckIn(checkIn: CheckIn, onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        try {
            val response = SicrediService.service.doCheckIn(checkIn)
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                onFailure?.invoke()
            }
        } catch (e: Exception) {
            onFailure?.invoke()
        }
    }
}