package com.sample.board.domain.model

import com.sample.board.domain.model.enumeration.MessageType
import java.util.*

data class Message(
    val id: Int,
    val messageType: MessageType,
    val postNo: Int,
    val replyNo: Int,
    val userId: String,
    val content: String,
    val isDeleted: Int,
    val createdAt: Date?,
    val updatedAt: Date?
)