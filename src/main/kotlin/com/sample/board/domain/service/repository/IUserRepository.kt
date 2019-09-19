package com.sample.board.domain.service.repository

import com.sample.board.domain.model.User

interface IUserRepository {
    fun fetchUserById(userId: String): User?
    fun create(user: User)
}