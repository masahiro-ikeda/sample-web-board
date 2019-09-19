package com.sample.board.datasource

import com.sample.board.datasource.mapper.MessageMapper
import com.sample.board.domain.model.Message
import com.sample.board.domain.service.dto.MessageDto
import com.sample.board.domain.service.repository.IPostRepository
import org.springframework.stereotype.Component

@Component
class PostRepositoryImpl(private val mapper: MessageMapper) : IPostRepository {

    override fun fetchAll(): List<Message>? {
        return mapper.select()
    }

    override fun create(message: MessageDto) {
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