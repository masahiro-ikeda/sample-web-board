package com.sample.board.query

import com.sample.board.domain.message.Good

interface IGoodQuery {
    fun fetchAllGood(): List<Good>?
    fun fetchGoodById(messageId: String): MutableList<Good>?
}