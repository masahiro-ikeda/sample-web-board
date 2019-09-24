package com.sample.board.infrastructure.query.message

import com.sample.board.domain.message.Good
import com.sample.board.infrastructure.domain.message.GoodQueryMapper
import com.sample.board.query.IGoodQuery
import org.springframework.stereotype.Component

@Component
class GoodQueryImpl(private val mapper: GoodQueryMapper) : IGoodQuery {

    override fun fetchAllGood(): List<Good>? {
        return mapper.select(null)
    }

    override fun fetchGoodById(messageId: String): List<Good>? {
        return mapper.select(messageId)
    }
}