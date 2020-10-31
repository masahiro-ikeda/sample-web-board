package com.sample.board.api.domain.query

import com.sample.board.api.domain.entity.message.Good

interface IGoodQuery {
    fun fetchAllGood(): List<Good>?
    fun fetchGoodById(messageId: String): MutableList<Good>?
}