package com.sample.board.api.infrastructure.repository.user

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.repository.IUserRepository
import org.springframework.stereotype.Component

/**
 * ユーザリポジトリの実装
 */
@Component
class UserRepositoryImpl(private val mapper: UserMapper) :
    IUserRepository {

    /**
     * ユーザを保存する
     *
     * @param user ユーザモデル
     */
    override fun save(user: User) {
        mapper.insert(user)
    }

    /**
     * ユーザを削除する
     *
     * @param user ユーザモデル
     */
    override fun delete(user: User) {
        // 未実装
    }
}