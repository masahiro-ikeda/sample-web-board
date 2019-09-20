package com.sample.board.application.dto

data class RegisterUserDto(
    val userId: String,
    val password: String,
    val userName: String,
    val userRole: String
)