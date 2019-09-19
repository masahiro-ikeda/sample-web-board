package com.sample.board.domain.service.dto

import com.sample.board.domain.model.enumeration.MessageType
import java.util.*

data class MessageDto(
    val messageType: MessageType,
    val postNo: Int,
    val replyNo: Int,
    val userId: String,
    val content: String,
    val isDeleted: Int
)