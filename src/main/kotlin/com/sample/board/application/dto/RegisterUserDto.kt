package com.sample.board.application.dto

import com.sample.board.domain.user.UserRole

data class RegisterUserDto(
    val userId: String,
    val password: String,
    val userName: String,
    val userRole: UserRole
)