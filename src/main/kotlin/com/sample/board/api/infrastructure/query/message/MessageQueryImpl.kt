package com.sample.board.api.infrastructure.query.message

import com.sample.board.api.domain.query.IMessageQuery
import com.sample.board.api.domain.query.dto.MessageDisplayDto
import org.springframework.stereotype.Component

@Component
class MessageQueryImpl(
    private val mapper: MessageQueryMapper
) : IMessageQuery {

    override fun findDisplayMessage(): List<MessageDisplayDto>? {
        return mapper.selectAll()
    }
}