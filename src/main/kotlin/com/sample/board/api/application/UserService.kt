package com.sample.board.api.application

import com.sample.board.api.common.exception.BusinessError
import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.entity.user.UserRole
import com.sample.board.api.domain.repository.IUserRepository
import com.sample.board.api.domain.service.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userDomainService: UserDomainService,
    private val userRepository: IUserRepository
) {

    /**
     * ユーザーの新規登録.
     */
    @Transactional
    fun create(id: String, password: String, name: String) {

        val user = User(id, password, name, UserRole.USER)

        // 重複チェック
        if (userDomainService.isExist(user)) {
            throw BusinessError("何かメッセージ")
        }

        userRepository.save(user)
    }
}