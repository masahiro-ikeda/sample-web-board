package com.sample.board.api.domain.repository.dto

data class UserDto(
    val id: String,
    val password: String,
    val name: String,
    val role: String,
    val isInvalid: Int
)