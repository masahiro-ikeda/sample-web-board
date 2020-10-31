package com.sample.board.api.domain.query

import com.sample.board.api.domain.entity.user.User

interface IUserQuery {
    fun findById(userId: String): User?
}