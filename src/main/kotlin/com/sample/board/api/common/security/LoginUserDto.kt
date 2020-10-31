package com.sample.board.api.common.security

data class LoginUserDto(
    val id: String,
    val password: String,
    val name: String,
    val role: String
)