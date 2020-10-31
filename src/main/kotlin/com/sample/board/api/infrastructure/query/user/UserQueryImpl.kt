package com.sample.board.api.infrastructure.query.user

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.query.IUserQuery
import org.springframework.stereotype.Component

@Component
class UserQueryImpl(private val mapper: UserQueryMapper) : IUserQuery {

    override fun findById(userId: String): User? {
        return mapper.selectById(userId)
    }
}