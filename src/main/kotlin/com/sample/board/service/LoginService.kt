package com.sample.board.service

import com.sample.board.domain.model.LoginUser
import com.sample.board.domain.model.User
import com.sample.board.service.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(val repository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(userId: String?): UserDetails {

        if (userId == null || "" == userId) {
            throw UsernameNotFoundException("ユーザーIDが未入力です")
        }

        val user: User = repository.fetchUserById(userId)
            ?: throw UsernameNotFoundException("ユーザーIDが不正です。")

        return LoginUser(user)
    }
}