package com.sample.board.infrastructure.domain.user

import com.sample.board.domain.user.IUserRepository
import com.sample.board.domain.user.User
import org.springframework.stereotype.Component

/**
 * ユーザリポジトリの実装
 */
@Component
class UserRepositoryImpl(private val mapper: UserMapper) : IUserRepository {

    /**
     * ユーザを保存する
     *
     * @param user ユーザモデル
     */
    override fun store(user: User) {
        mapper.insert(user)
    }
}