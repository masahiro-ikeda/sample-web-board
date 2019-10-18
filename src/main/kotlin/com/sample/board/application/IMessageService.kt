package com.sample.board.application

import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageForDisplay

interface IMessageService {
    fun postMessage(input: PostMessageDto)
    fun postReply(input: PostReplyDto)
    fun deleteMessage(messageId: String, loginUserId: String)
    fun getMessages(): List<MessageForDisplay>
}