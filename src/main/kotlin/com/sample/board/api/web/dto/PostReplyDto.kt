package com.sample.board.api.web.dto

data class PostReplyDto(
    val userId: String,
    val messageId: Int,
    val comment: String?
)