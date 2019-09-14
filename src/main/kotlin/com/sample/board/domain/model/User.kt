package com.sample.board.domain.model

import com.sample.board.domain.model.enumeration.UserRole
import java.util.*

data class User(
    val id: String,
    val password: String,
    val name: String,
    val role: UserRole,
    val isInvalid: Int,
    val createdAt: Date?,
    val updatedAt: Date?
)



