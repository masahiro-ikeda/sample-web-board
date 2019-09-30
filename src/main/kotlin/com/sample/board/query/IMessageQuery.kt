package com.sample.board.query

import com.sample.board.query.dto.MessageDto

interface IMessageQuery {
    fun fetchAll(): List<MessageDto>?
    fun fetchById(id: String): MessageDto?
    fun fetchByPostNo(postNo: Int): List<MessageDto>?
    fun fetchMaxPostNo(): Int
    fun fetchMaxReplyNo(postNo: Int): Int
}