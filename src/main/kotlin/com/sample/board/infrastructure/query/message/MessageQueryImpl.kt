package com.sample.board.infrastructure.domain.message

import com.sample.board.domain.message.Message
import com.sample.board.query.IMessageQuery
import com.sample.board.query.dto.MessageDto
import org.springframework.stereotype.Component

@Component
class MessageQueryImpl(private val mapper: MessageQueryMapper) : IMessageQuery {

    override fun fetchAll(): List<MessageDto>? {
        return mapper.selectAll()
    }

    override fun fetchById(id: String): Message? {
        return mapper.selectById(id)
    }

    override fun fetchMaxPostNo(): Int {
        return mapper.selectMaxPostNo()
    }

    override fun fetchMaxReplyNo(postNo: Int): Int {
        return mapper.selectMaxReplyNo(postNo)
    }
}