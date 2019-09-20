package com.sample.board.application

import com.sample.board.application.dto.DisplayMessageDto
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.IMessageRepository
import com.sample.board.domain.message.Message
import com.sample.board.domain.message.MessageType
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    private val repository: IMessageRepository
) : IMessageService {

    override fun postMessage(dto: PostMessageDto) {

        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.MESSAGE.name,
            repository.fetchMaxPostNo() + 1,
            0,
            dto.userId,
            dto.comment,
            0
        )
        repository.create(message)
    }

    override fun postReply(dto: PostReplyDto) {

        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.REPLY.name,
            dto.postNo,
            repository.fetchMaxReplyNo(dto.postNo) + 1,
            dto.userId,
            dto.comment,
            0
        )
        repository.create(message)
    }

    override fun delete(id: String) {

    }

    override fun displayBoard(): List<DisplayMessageDto> {

        return repository.fetchAll()!!
    }
}