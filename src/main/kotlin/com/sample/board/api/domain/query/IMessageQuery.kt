package com.sample.board.api.domain.query

import com.sample.board.api.domain.query.dto.MessageDisplayDto

interface IMessageQuery {
    fun findDisplayMessage(): List<MessageDisplayDto>?
}