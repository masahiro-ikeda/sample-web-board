package com.sample.board.domain.service

import com.sample.board.domain.model.User
import com.sample.board.domain.model.enumeration.UserRole
import com.sample.board.domain.service.dto.UserCreateDto
import com.sample.board.domain.service.exception.CreateUserException
import com.sample.board.domain.service.message.MessageResources
import com.sample.board.domain.service.repository.IUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val errorMessage: MessageResources,
    private val repository: IUserRepository
) : IUserService {

    override fun createUser(dto: UserCreateDto) {

        // ユーザーモデルの生成
        val user = User(
            dto.userId,
            BCryptPasswordEncoder().encode(dto.password),
            dto.userName,
            UserRole.USER,
            0,
            null,
            null
        )

        // IDの重複チェック
        if (repository.fetchUserById(user.id) != null) {
            val message = errorMessage.get("error.application.duplicating", "ユーザーID")
            throw CreateUserException(message)
        }

        repository.create(user)
    }
}