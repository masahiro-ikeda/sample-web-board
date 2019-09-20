package com.sample.board.application

import com.sample.board.application.dto.DisplayMessageDto
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto

interface IMessageService {
    fun postMessage(dto: PostMessageDto)
    fun postReply(dto: PostReplyDto)
    fun delete(id: String)
    fun displayBoard(): List<DisplayMessageDto>
}