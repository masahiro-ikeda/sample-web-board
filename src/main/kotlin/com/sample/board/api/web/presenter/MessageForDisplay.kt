package com.sample.board.api.web.presenter

import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.entity.message.MessageType
import java.time.LocalDateTime

/**
 * 表示用メッセージDTO
 */
data class MessageForDisplay(
    val messageId: Int,
    val messageType: MessageType,
    val replyTo: Int?,
    val userId: String,
    val userName: String,
    val comment: String,
    val isDeleted: Int,
    val goods: List<Good>,
    val updatedAt: LocalDateTime
)