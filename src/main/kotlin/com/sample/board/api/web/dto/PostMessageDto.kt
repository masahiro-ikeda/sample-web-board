package com.sample.board.api.web.dto

data class PostMessageDto(
    val userId: String,
    val comment: String?
)