package com.sample.board.domain.message

import com.sample.board.application.dto.DisplayMessageDto

interface IMessageRepository {
    fun fetchAll(): List<DisplayMessageDto>?
    fun create(message: Message)
    fun delete(id: Int)
    fun fetchMaxPostNo(): Int
    fun fetchMaxReplyNo(postNo: Int): Int
}