package com.sample.board.api.domain.repository.dto

data class GoodDto(
    val id: Int,
    val messageId: Int,
    val userId: String,
    val isDeleted: Int
)