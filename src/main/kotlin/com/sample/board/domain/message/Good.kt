package com.sample.board.domain.message

data class Good(
    val id: String,
    val messageId: String,
    val userId: String,
    val isDelete: Boolean
)