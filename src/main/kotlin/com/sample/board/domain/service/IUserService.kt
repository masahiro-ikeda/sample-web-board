package com.sample.board.domain.service

import com.sample.board.domain.service.dto.UserCreateDto
import org.springframework.stereotype.Service

interface IUserService {
    fun createUser(dto: UserCreateDto)
}