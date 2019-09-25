package com.sample.board.query

import com.sample.board.domain.user.User

interface IUserQuery {
    fun fetchUserById(userId: String): User?
}