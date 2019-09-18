package com.sample.board.domain.service.dto

data class UserCreateDto(
    val userId: String,
    val password: String,
    val userName: String
)