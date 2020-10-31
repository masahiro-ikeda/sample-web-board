package com.sample.board.api.domain.query.dto

import java.time.LocalDateTime

data class MessageDisplayDto(
    val id: Int,
    val type: String,
    val replyTo: Int?,
    val userId: String,
    val userName: String,
    val comment: String,
    val isDeleted: Int,
    val UpdatedAt: LocalDateTime
)