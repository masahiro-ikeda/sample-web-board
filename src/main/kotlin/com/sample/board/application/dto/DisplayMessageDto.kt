package com.sample.board.application.dto

import com.sample.board.domain.message.MessageType
import java.time.LocalDateTime

data class DisplayMessageDto(
    val id: String,
    val type: MessageType,
    val postNo: Int,
    val replyNo: Int,
    val userId: String,
    val userName: String,
    val content: String,
    val isDeleted: Int,
    val postTime: LocalDateTime
)