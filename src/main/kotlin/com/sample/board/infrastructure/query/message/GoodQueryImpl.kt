package com.sample.board.infrastructure.query.message

import com.sample.board.domain.message.Good
import com.sample.board.query.IGoodQuery
import org.springframework.stereotype.Component

@Component
class GoodQueryImpl(private val mapper: GoodQueryMapper) : IGoodQuery {

    override fun fetchAllGood(): List<Good>? {
        return mapper.selectAll()
    }

    override fun fetchGoodById(messageId: String): MutableList<Good>? {
        return mapper.selectByMessageId(messageId)
    }
}