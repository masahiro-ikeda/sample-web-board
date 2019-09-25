package com.sample.board.application

import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.RemoveGoodDto
import com.sample.board.domain.message.Good
import com.sample.board.domain.message.IMessageRepository
import com.sample.board.domain.message.Message
import com.sample.board.query.IGoodQuery
import com.sample.board.query.IMessageQuery
import org.springframework.stereotype.Service
import java.util.*

@Service
class GoodService(
    private val repository: IMessageRepository,
    private val query: IMessageQuery,
    private val goodQuery: IGoodQuery
) {

    fun postGood(dto: PostGoodDto) {
        val messageDto = query.fetchById(dto.messageId) ?: throw IllegalArgumentException()
        val goodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()

        val message = Message(
            messageDto.id,
            messageDto.type.name,
            messageDto.postNo,
            messageDto.replyNo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goodList
        )
        message.addGood(
            Good(UUID.randomUUID().toString(), dto.messageId, dto.userId, 0)
        )

        repository.store(message)
    }

    fun removeGood(dto: RemoveGoodDto) {
        val messageDto = query.fetchById(dto.messageId) ?: throw IllegalArgumentException()
        val goodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()

        val message = Message(
            messageDto.id,
            messageDto.type.name,
            messageDto.postNo,
            messageDto.replyNo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goodList
        )
        message.removeGood(dto.userId)
        repository.store(message)
    }
}