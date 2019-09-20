package com.sample.board.domain.service

import com.sample.board.application.IUserService
import com.sample.board.application.dto.RegisterUserDto
import com.sample.board.domain.user.IUserRepository
import com.sample.board.domain.user.User
import com.sample.board.application.message.MessageResources
import org.springframework.stereotype.Service

@Service
class UserService(
    private val errorMessage: MessageResources,
    private val repository: IUserRepository
) : IUserService {

    override fun registerUser(dto: RegisterUserDto) {

        // ユーザーモデルの生成
        val user = User(
            dto.userId,
            dto.password,
            dto.userName,
            "USER",
            0
        )

        // IDの重複チェック
        if (repository.fetchUserById(user.id) != null) {
            val message = errorMessage.get("error.application.duplicating", "ユーザーID")
            throw IllegalArgumentException(message)
        }

        repository.create(user)
    }
}