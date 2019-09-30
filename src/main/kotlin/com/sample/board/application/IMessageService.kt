package com.sample.board.application

import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageForDisplay

interface IMessageService {
    fun postMessage(dto: PostMessageDto)
    fun postReply(dto: PostReplyDto)
    fun deleteMessage(messageId: String)
    fun fetchAllMessage(): List<MessageForDisplay>
}