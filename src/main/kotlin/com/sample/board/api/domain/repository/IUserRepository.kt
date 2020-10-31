package com.sample.board.api.domain.repository

import com.sample.board.api.domain.entity.user.User

interface IUserRepository {
    fun save(user: User)
    fun delete(user: User)
}