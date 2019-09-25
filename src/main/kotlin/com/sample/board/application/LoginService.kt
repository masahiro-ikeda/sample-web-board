package com.sample.board.application

import com.sample.board.application.message.MessageResources
import com.sample.board.domain.user.LoginUser
import com.sample.board.domain.user.User
import com.sample.board.query.IUserQuery
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(val query: IUserQuery, private val messageResource: MessageResources) : UserDetailsService {

    override fun loadUserByUsername(userId: String?): UserDetails {

        // ユーザIDが未入力
        if (userId.isNullOrBlank()) {
            throw UsernameNotFoundException(messageResource.get("error.login.userId.null"))
        }

        // ユーザが存在しない
        val user: User = query.fetchUserById(userId)
            ?: throw UsernameNotFoundException(messageResource.get("error.login.user.notExist"))

        return LoginUser(user)
    }
}