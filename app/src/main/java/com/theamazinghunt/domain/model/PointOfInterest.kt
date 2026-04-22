package com.theamazinghunt.domain.model

data class PointOfInterest(
    val id: String,
    val title: String,
    val description: String,
    val location: GeoPoint,
    val orderIndex: Int
)
