package com.sample.board.api.domain.repository

import com.sample.board.api.domain.entity.message.Message

interface IMessageRepository {
    fun save(message: Message)
    fun delete(message: Message)
}