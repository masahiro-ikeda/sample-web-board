package com.sample.board.api.domain.query.dto

import com.sample.board.api.domain.entity.message.MessageType
import java.time.LocalDateTime

data class MessageDto(
    val id: String,
    val type: MessageType,
    val postNo: Int,
    val replyNo: Int,
    val userId: String,
    val userName: String,
    val comment: String,
    val isDeleted: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)