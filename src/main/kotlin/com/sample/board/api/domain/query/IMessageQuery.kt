package com.sample.board.api.domain.query

import com.sample.board.api.domain.query.dto.MessageDto

interface IMessageQuery {
    fun fetchAll(): List<MessageDto>?
    fun fetchById(id: String): MessageDto?
    fun fetchMaxPostNo(): Int
    fun fetchMaxReplyNo(postNo: Int): Int
}