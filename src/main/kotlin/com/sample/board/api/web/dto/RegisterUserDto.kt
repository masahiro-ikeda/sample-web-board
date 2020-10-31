package com.sample.board.api.web.dto

import com.sample.board.api.domain.entity.user.UserRole

data class RegisterUserDto(
    val userId: String,
    val password: String,
    val userName: String,
    val userRole: UserRole
)