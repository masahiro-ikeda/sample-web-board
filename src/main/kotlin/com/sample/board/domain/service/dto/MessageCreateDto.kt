package com.sample.board.domain.service.dto

import com.sample.board.domain.model.enumeration.MessageType

data class MessageCreateDto(
    val messageType: MessageType,
    val postNo: Int,
    val userId: String,
    val content: String
)