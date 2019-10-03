package com.sample.board.application.implement

import com.sample.board.application.IUserService
import com.sample.board.application.dto.RegisterUserDto
import com.sample.board.application.exception.DuplicateUserException
import com.sample.board.domain.user.IUserRepository
import com.sample.board.domain.user.User
import com.sample.board.domain.user.UserRole
import com.sample.board.query.IUserQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(

    /** ユーザリポジトリ */
    private val repository: IUserRepository,
    /** ユーザクエリ */
    private val query: IUserQuery

) : IUserService {

    /**
     * 一般ユーザーの新規登録
     *
     * @param dto ユーザ登録DTO
     */
    @Transactional
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
            // 登録済みのため例外スロー
            throw DuplicateUserException()
        }

        // 永続化
        repository.store(user)
    }
}