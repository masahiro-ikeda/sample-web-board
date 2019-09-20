package com.sample.board.domain.user

interface IUserRepository {
    fun fetchUserById(userId: String): User?
    fun create(user: User)
}