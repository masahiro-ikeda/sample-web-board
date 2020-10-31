package com.sample.board.api.infrastructure.repository.user

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.repository.IUserRepository
import com.sample.board.api.domain.repository.dto.UserDto
import org.springframework.stereotype.Component

/**
 * ユーザリポジトリの実装
 */
@Component
class UserRepositoryImpl(
    private val mapper: UserMapper
) : IUserRepository {

    /**
     * ユーザーを取得する.
     */
    override fun findById(id: String): UserDto? {
        return mapper.selectById(id)
    }

    /**
     * ユーザを保存する.
     *
     * @param user ユーザモデル
     */
    override fun save(user: User) {
        if (mapper.selectById(user.getId()) == null) {
            mapper.insert(user)
        } else {
            mapper.update(user)
        }
    }
}