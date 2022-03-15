package com.mlodi.sicredi.devandroidtest.data.api.model

data class Event(
    val id: String,
    val date: Long,
    val description: String,
    val title: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val price: Double,
    val people: List<String>
)