package com.sample.board.query

import com.sample.board.domain.message.Message
import com.sample.board.query.dto.MessageDto

interface IMessageQuery {
    fun fetchAll(): List<MessageDto>?
    fun fetchById(id: String): Message?
    fun fetchMaxPostNo(): Int
    fun fetchMaxReplyNo(postNo: Int): Int
}