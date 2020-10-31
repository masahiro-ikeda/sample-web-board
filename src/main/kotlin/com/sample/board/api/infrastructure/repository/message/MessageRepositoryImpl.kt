package com.sample.board.api.infrastructure.repository.message

import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.repository.IMessageRepository
import com.sample.board.api.domain.repository.dto.MessageDto
import org.springframework.stereotype.Component

@Component
class MessageRepositoryImpl(
    private val messageMapper: MessageMapper,
    private val goodMapper: GoodMapper
) : IMessageRepository {

    /**
     * 取得.
     */
    override fun findById(id: Int): MessageDto? {
        return messageMapper.selectById(id)
    }

    /**
     * 保存.
     */
    override fun save(message: Message) {

        if (message.getId() == null) {
            messageMapper.insert(message)
        } else {
            messageMapper.update(message)
            for (good in message.getGoods()) {
                if (good.getId() == null) {
                    goodMapper.insert(good)
                } else {
                    goodMapper.update(good)
                }

            }
        }
    }
}