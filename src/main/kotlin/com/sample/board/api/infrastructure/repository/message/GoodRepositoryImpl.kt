package com.sample.board.api.infrastructure.repository.message

import com.sample.board.api.domain.repository.IGoodRepository
import com.sample.board.api.domain.repository.dto.GoodDto
import org.springframework.stereotype.Component

@Component
class GoodRepositoryImpl(
    private val goodMapper: GoodMapper
) : IGoodRepository {

    override fun findByMessageId(messageId: Int): MutableList<GoodDto>? {

        val goods = goodMapper.selectByMessageId(messageId)

        if (goods == null) {
            return mutableListOf()
        } else {
            return goodMapper.selectByMessageId(messageId)!!.toMutableList()
        }
    }

    override fun findAll(): List<GoodDto>? {
        return goodMapper.selectAll()
    }

}