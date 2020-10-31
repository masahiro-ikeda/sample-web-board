package com.sample.board.api.common.security

import com.sample.board.api.domain.entity.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LoginUser(private val user: User) : UserDetails {

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.id
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return user.isInvalid == 0
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        var authorities: MutableList<GrantedAuthority> = mutableListOf()
        authorities.add(SimpleGrantedAuthority(user.role.name))
        return authorities
    }
}