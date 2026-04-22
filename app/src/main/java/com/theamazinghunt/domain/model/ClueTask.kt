package com.theamazinghunt.domain.model

data class ClueTask(
    val id: String,
    val pointOfInterestId: String,
    val clueText: String,
    val taskText: String,
    val hintText: String?,
    val orderIndex: Int
)
