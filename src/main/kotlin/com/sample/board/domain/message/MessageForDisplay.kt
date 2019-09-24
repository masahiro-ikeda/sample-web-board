package com.sample.board.domain.message

import java.time.LocalDateTime

data class MessageForDisplay(
    val body: Message,
    val userName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)