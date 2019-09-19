package com.sample.board.domain.service.repository

import com.sample.board.domain.model.Message
import com.sample.board.domain.service.dto.MessageDto

interface IPostRepository {
    fun fetchAll(): List<Message>?
    fun create(message: MessageDto)
    fun delete(id: Int)
    fun fetchMaxPostNo(): Int
    fun fetchMaxReplyNo(postNo: Int): Int
}