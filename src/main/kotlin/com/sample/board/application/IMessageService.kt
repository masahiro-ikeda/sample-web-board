package com.sample.board.application

import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageForDisplay
import com.sample.board.domain.user.LoginUser

interface IMessageService {
    fun postMessage(dto: PostMessageDto)
    fun postReply(dto: PostReplyDto)
    fun deleteMessage(messageId: String, loginUser: LoginUser)
    fun getMessages(): List<MessageForDisplay>
}