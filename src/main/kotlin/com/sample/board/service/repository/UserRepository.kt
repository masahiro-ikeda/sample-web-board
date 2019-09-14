package com.sample.board.service.repository

import com.sample.board.domain.model.User

interface UserRepository {
    fun fetchUserById(userId: String): User?

    fun createUser(user: User)
}