package com.sample.board.api.domain.service

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.query.IUserQuery

class UserDomainService(
    private val userQuery: IUserQuery
) {

    /**
     * ユーザーの重複チェック.
     */
    fun isExist(user: User): Boolean {

        val duplicatedUser: User? = userQuery.findById(user.id);
        return duplicatedUser != null
    }
}