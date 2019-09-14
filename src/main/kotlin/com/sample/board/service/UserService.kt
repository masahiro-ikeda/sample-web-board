package com.sample.board.service

import com.sample.board.domain.model.User
import com.sample.board.domain.model.enumeration.UserRole
import com.sample.board.service.dto.UserCreateDto
import com.sample.board.service.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserRepository) {

    fun createUser(dto: UserCreateDto) {

        // エンコーダーの呼び出し
        val passwordEncoder = BCryptPasswordEncoder()

        // ユーザーモデルの生成
        val user = User(
            dto.userId,
            passwordEncoder.encode(dto.password),
            dto.userName,
            UserRole.USER,
            0,
            null,
            null
        )

        repository.createUser(user)
    }
}