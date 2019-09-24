package com.sample.board.application

import com.sample.board.query.dto.MessageDto
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageForDisplay

interface IMessageService {
    fun postMessage(dto: PostMessageDto)
    fun postReply(dto: PostReplyDto)
    fun delete(id: String)
    fun fetchMessages(): List<MessageForDisplay>
}