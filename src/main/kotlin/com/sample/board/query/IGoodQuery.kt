package com.sample.board.query

import com.sample.board.domain.message.Good
import com.sample.board.query.dto.MessageDto

interface IGoodQuery {
    fun fetchAllGood(): List<Good>?
    fun fetchGoodById(messageId: String): List<Good>?
}