package com.sample.board.api.domain.repository

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.repository.dto.UserDto

interface IUserRepository {
    fun findById(id: String): UserDto?
    fun save(user: User)
}