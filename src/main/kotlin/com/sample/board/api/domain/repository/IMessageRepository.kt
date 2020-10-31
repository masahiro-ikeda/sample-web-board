package com.sample.board.api.domain.repository

import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.repository.dto.MessageDto

interface IMessageRepository {
    fun findById(id: Int): MessageDto?
    fun save(message: Message)
}