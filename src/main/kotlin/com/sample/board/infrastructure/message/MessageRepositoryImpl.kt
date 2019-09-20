package com.sample.board.infrastructure.message

import com.sample.board.application.dto.DisplayMessageDto
import com.sample.board.domain.message.IMessageRepository
import com.sample.board.domain.message.Message
import org.springframework.stereotype.Component

@Component
class MessageRepositoryImpl(private val mapper: MessageMapper) : IMessageRepository {

    override fun fetchAll(): List<DisplayMessageDto>? {
        return mapper.select()
    }

    override fun create(message: Message) {
        mapper.insert(message)
    }

    override fun delete(id: Int) {
        mapper.delete(id)
    }

    override fun fetchMaxPostNo(): Int {
        return mapper.selectMaxPostNo()
    }

    override fun fetchMaxReplyNo(postNo: Int): Int {
        return mapper.selectMaxReplyNo(postNo)
    }
}