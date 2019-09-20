package com.sample.board.application

import com.sample.board.application.dto.RegisterUserDto

interface IUserService {
    fun registerUser(from: RegisterUserDto)
}