package com.sample.board.api.infrastructure.query.message

import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.query.IGoodQuery
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