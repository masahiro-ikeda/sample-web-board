package com.sample.board.domain.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class User{
    val id: String
    val password: String
    val name: String
    val role: UserRole
    val isInvalid: Int

    constructor(id: String, password: String, name: String, role: String, isInvalid: Int) {
        this.id = id
        this.password = BCryptPasswordEncoder().encode(password)
        this.name = name
        this.role = UserRole.valueOf(role)
        this.isInvalid = isInvalid
    }
}


