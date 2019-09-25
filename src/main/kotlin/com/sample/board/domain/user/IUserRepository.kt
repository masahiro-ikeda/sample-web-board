package com.sample.board.domain.user

interface IUserRepository {
    fun store(user: User)
}