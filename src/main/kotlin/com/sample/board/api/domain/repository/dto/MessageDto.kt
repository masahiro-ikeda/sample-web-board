package com.sample.board.api.domain.repository.dto

data class MessageDto(
    val id: Int,
    val type: String,
    val replyTo: Int?,
    val userId: String,
    val comment: String,
    val isDeleted: Int
)