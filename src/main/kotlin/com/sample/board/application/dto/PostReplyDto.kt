package com.sample.board.application.dto

data class PostReplyDto(
    val userId: String,
    val messageId: String,
    val comment: String?
)