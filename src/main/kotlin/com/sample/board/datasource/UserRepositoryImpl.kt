package com.sample.board.datasource

import com.sample.board.datasource.mapper.UserMapper
import com.sample.board.domain.model.User
import com.sample.board.domain.service.repository.IUserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(private val mapper: UserMapper): IUserRepository {

    override fun fetchUserById(userId: String): User? {
        return mapper.selectById(userId)
    }

    override fun createUser(user: User) {
        mapper.insert(user)
    }
}