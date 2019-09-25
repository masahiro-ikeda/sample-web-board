package com.sample.board.application

import com.sample.board.application.dto.RegisterUserDto
import com.sample.board.application.message.MessageResources
import com.sample.board.domain.user.IUserRepository
import com.sample.board.domain.user.User
import com.sample.board.domain.user.UserRole
import com.sample.board.query.IUserQuery
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: IUserRepository,
    private val query: IUserQuery,
    private val errorMessage: MessageResources
) : IUserService {

    /**
     * ユーザーの新規登録
     *
     * @param dto 登録ユーザDTO
     */
    override fun registerUser(dto: RegisterUserDto) {

        // ユーザーモデルの生成
        val user = User(
            dto.userId,
            dto.password,
            dto.userName,
            UserRole.USER.name,
            0
        )

        // IDの重複チェック
        if (query.fetchUserById(user.id) != null) {
            val message = errorMessage.get("error.application.duplicating", "ユーザーID")
            throw IllegalArgumentException(message)
        }

        // 保存
        repository.store(user)
    }
}