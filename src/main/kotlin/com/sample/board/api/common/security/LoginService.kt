package com.sample.board.api.common.security

import com.sample.board.api.domain.repository.IUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val repository: IUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(userId: String?): UserDetails {

        // ユーザIDが未入力
        if (userId.isNullOrBlank()) {
            throw UsernameNotFoundException("error.login.userId.null")
        }

        // ユーザが存在しない
        val user = repository.findById(userId)
        if (user == null){
            throw UsernameNotFoundException("error.login.user.notExist")
        }

        return LoginUser(user)
    }
}