package com.mlodi.sicredi.devandroidtest.data.api.service

import com.mlodi.sicredi.devandroidtest.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SicrediService {
    val service: SicrediServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SICREDI_API_PRD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SicrediServiceInterface::class.java)
    }
}