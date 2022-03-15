package com.mlodi.sicredi.devandroidtest.data.api.service

import com.mlodi.sicredi.devandroidtest.data.api.model.CheckIn
import com.mlodi.sicredi.devandroidtest.data.api.model.Event
import com.mlodi.sicredi.devandroidtest.data.api.util.ApiUtil.CHECKIN
import com.mlodi.sicredi.devandroidtest.data.api.util.ApiUtil.EVENT
import com.mlodi.sicredi.devandroidtest.data.api.util.ApiUtil.EVENTS
import com.mlodi.sicredi.devandroidtest.data.api.util.ApiUtil.EVENT_ID
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SicrediServiceInterface {

    @GET(EVENTS)
    suspend fun getEvents(): Response<List<Event>>

    @GET(EVENT)
    suspend fun getEvent(@Path(EVENT_ID) id: String): Response<Event>

    @POST(CHECKIN)
    suspend fun doCheckIn(@Body checkIn: CheckIn): Response<ResponseBody>
}