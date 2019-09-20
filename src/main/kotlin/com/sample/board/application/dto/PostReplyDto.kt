package com.sample.board.application.dto

data class PostReplyDto(
    val postNo: Int,
    val userId: String,
    val comment: String
)