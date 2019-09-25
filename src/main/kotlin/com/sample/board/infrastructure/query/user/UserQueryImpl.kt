package com.sample.board.infrastructure.query.user

import com.sample.board.domain.user.User
import com.sample.board.query.IUserQuery
import org.springframework.stereotype.Component

@Component
class UserQueryImpl(private val mapper: UserQueryMapper) : IUserQuery {

    override fun fetchUserById(userId: String): User? {
        return mapper.selectById(userId)
    }
}