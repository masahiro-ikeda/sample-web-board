package com.sample.board.api.infrastructure.query.message

import com.sample.board.api.domain.query.IMessageQuery
import com.sample.board.api.domain.query.dto.MessageDto
import org.springframework.stereotype.Component

@Component
class MessageQueryImpl(private val mapper: MessageQueryMapper) : IMessageQuery {

    override fun fetchAll(): List<MessageDto>? {
        return mapper.selectAll()
    }

    override fun fetchById(id: String): MessageDto? {
        return mapper.selectById(id)
    }

    override fun fetchMaxPostNo(): Int {
        return mapper.selectMaxPostNo()
    }

    override fun fetchMaxReplyNo(postNo: Int): Int {
        return mapper.selectMaxReplyNo(postNo)
    }
}