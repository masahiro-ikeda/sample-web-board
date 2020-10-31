package com.sample.board.api.domain.service

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.repository.IUserRepository
import com.sample.board.api.domain.repository.dto.UserDto
import org.springframework.stereotype.Component

@Component
class UserDomainService(
    private val userRepository: IUserRepository
) {

    /**
     * ユーザーの重複チェック.
     */
    fun isExist(user: User): Boolean {

        val duplicatedUser: UserDto? = userRepository.findById(user.getId())
        return duplicatedUser != null
    }
}