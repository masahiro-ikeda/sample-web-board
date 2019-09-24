package com.sample.board.infrastructure.domain.user

import com.sample.board.domain.user.User
import com.sample.board.domain.user.IUserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(private val mapper: UserMapper): IUserRepository {

    override fun fetchUserById(userId: String): User? {
        return mapper.selectById(userId)
    }

    override fun create(user: User) {
        mapper.insert(user)
    }
}