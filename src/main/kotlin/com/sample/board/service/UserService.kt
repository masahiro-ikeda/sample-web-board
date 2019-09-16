package com.sample.board.service

import com.sample.board.domain.model.User
import com.sample.board.domain.model.enumeration.UserRole
import com.sample.board.service.dto.UserCreateDto
import com.sample.board.service.exception.CreateUserException
import com.sample.board.service.message.ErrorMessageResource
import com.sample.board.service.repository.UserRepository
import com.sample.board.service.validation.Validator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val errorMessage: ErrorMessageResource,
    private val repository: UserRepository
) {

    fun createUser(dto: UserCreateDto): Result {

        val result = Result()

        // 形式チェック
        try {
            Validator(dto.userId, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
                .checkMatchPattern("^[a-zA-Z0-9]+\$")
                .execute()
        } catch (e: CreateUserException) {
            result.addError("userId", e.message!!)
            result.setFailure()
        }

        try {
            Validator(dto.password, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
                .checkMatchPattern("^[a-zA-Z0-9]+\$")
                .execute()
        } catch (e: CreateUserException) {
            result.addError("password", e.message!!)
            result.setFailure()
        }

        try {
            Validator(dto.userName, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
                .execute()
        } catch (e: CreateUserException) {
            result.addError("userName", e.message!!)
            result.setFailure()
        }

        // IDの重複チェック
        if (repository.fetchUserById(dto.userId!!) != null) {
            val message = errorMessage.get("error.application.duplicating", "ユーザーID")
            result.addError("userId", message)
            result.setFailure()
        }

        // ユーザーモデルの生成
        val user = User(
            dto.userId,
            BCryptPasswordEncoder().encode(dto.password),
            dto.userName!!,
            UserRole.USER,
            0,
            null,
            null
        )
        repository.createUser(user)

        return result
    }
}