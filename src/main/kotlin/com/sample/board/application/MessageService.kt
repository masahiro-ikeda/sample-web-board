package com.sample.board.application

import com.sample.board.query.dto.MessageDto
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.*
import com.sample.board.query.IGoodQuery
import com.sample.board.query.IMessageQuery
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class MessageService(
    private val repository: IMessageRepository,
    private val query: IMessageQuery,
    private val goodQuery: IGoodQuery

) : IMessageService {

    override fun postMessage(dto: PostMessageDto) {

        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.MESSAGE.name,
            query.fetchMaxPostNo() + 1,
            0,
            dto.userId,
            dto.comment,
            0,
            mutableListOf<Good>()
        )
        repository.store(message)
    }

    override fun postReply(dto: PostReplyDto) {

        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.REPLY.name,
            dto.postNo,
            query.fetchMaxReplyNo(dto.postNo) + 1,
            dto.userId,
            dto.comment,
            0,
            mutableListOf()
        )
        repository.store(message)
    }

    override fun delete(id: String) {

    }

    override fun fetchMessages(): List<MessageForDisplay> {

        var messageForDisplayList = mutableListOf<MessageForDisplay>()
        val allMessage = query.fetchAll() ?: listOf()
        val allGood = goodQuery.fetchAllGood() ?: listOf()

        allMessage.forEach { dto ->
            val id = dto.id
            val goods = allGood.stream().filter{good -> id.equals(good.messageId)}.collect(Collectors.toList())
            val message = Message(
                dto.id,
                dto.type.name,
                dto.postNo,
                dto.replyNo,
                dto.userId,
                dto.comment,
                dto.isDeleted,
                goods
            )
            messageForDisplayList.add(MessageForDisplay(message, dto.userName, dto.createdAt, dto.updatedAt))
        }

        return messageForDisplayList
    }
}