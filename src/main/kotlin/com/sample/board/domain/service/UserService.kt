package com.sample.board.domain.service

import com.sample.board.domain.model.User
import com.sample.board.domain.model.enumeration.UserRole
import com.sample.board.domain.service.dto.UserCreateDto
import com.sample.board.domain.service.exception.CreateUserException
import com.sample.board.domain.service.exception.ValidationErrorException
import com.sample.board.domain.service.message.ErrorMessageResource
import com.sample.board.domain.service.repository.IUserRepository
import com.sample.board.domain.service.validation.Validator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val errorMessage: ErrorMessageResource,
    private val repository: IUserRepository
) {

    fun createUser(dto: UserCreateDto): ServiceResult {

        val result = ServiceResult()

        // userIdの形式チェック
        try {
            Validator(dto.userId, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
                .checkMatchPattern("^[a-zA-Z0-9]+\$")
        } catch (e: ValidationErrorException) {
            result.addError("userId", e)
        }

        // passwordの形式チェック
        try {
            Validator(dto.password, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
                .checkMatchPattern("^[a-zA-Z0-9]+\$")
        } catch (e: ValidationErrorException) {
            result.addError("password", e)
        }

        // userNameの形式チェック
        try {
            Validator(dto.userName, errorMessage)
                .checkNotEmpty()
                .checkSize(25)
        } catch (e: ValidationErrorException) {
            result.addError("userName", e)
        }

        // バリデーション不正の場合は早期リターン
        if (!result.isSuccess()) {
            return result
        }

        // ユーザーモデルの生成
        val user = User(
            dto.userId!!,
            BCryptPasswordEncoder().encode(dto.password!!),
            dto.userName!!,
            UserRole.USER,
            0,
            null,
            null
        )

        // IDの重複チェック
        if (repository.fetchUserById(user.id) != null) {
            val message = errorMessage.get("error.application.duplicating", "ユーザーID")
            result.addError("userId", CreateUserException(message))
            return result
        }

        repository.createUser(user)
        return result
    }
}