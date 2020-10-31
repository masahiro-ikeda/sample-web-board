package com.sample.board.api.domain.repository

import com.sample.board.api.domain.repository.dto.GoodDto

interface IGoodRepository {
    fun findAll(): List<GoodDto>?
    fun findByMessageId(messageId: Int): MutableList<GoodDto>?
}