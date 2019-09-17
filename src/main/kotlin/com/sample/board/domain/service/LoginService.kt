package com.sample.board.domain.service

import com.sample.board.domain.model.LoginUser
import com.sample.board.domain.model.User
import com.sample.board.domain.service.repository.IUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(val repository: IUserRepository) : UserDetailsService {

    override fun loadUserByUsername(userId: String?): UserDetails {

        if (userId.isNullOrBlank()) {
            throw UsernameNotFoundException("ユーザーIDが未入力です")
        }

        val user: User = repository.fetchUserById(userId)
            ?: throw UsernameNotFoundException("ユーザーIDが不正です。")

        return LoginUser(user)
    }
}