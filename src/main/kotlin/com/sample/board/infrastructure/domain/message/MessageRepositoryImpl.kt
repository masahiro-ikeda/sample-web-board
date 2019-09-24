package com.sample.board.infrastructure.domain.message

import com.sample.board.domain.message.IMessageRepository
import com.sample.board.domain.message.Message
import org.springframework.stereotype.Component

@Component
class MessageRepositoryImpl(
    private val messageMapper: MessageMapper,
    private val goodMapper: GoodMapper
) : IMessageRepository {

    override fun store(message: Message) {
        if (messageMapper.selectById(message.id).isNullOrEmpty()) {
            messageMapper.insert(message)
        }

        val savedList = goodMapper.selectByMessageId(message.id)
        val newerList = message.goodList

        // いいねの追加を反映
        newerList.stream().filter { newer ->
            !savedList.stream().anyMatch { saved -> newer.id.equals(saved.id) }
        }
            .forEach {
                goodMapper.insert(it)
            }

        // いいねの削除を反映
        savedList.stream().filter { saved ->
            !newerList.stream().anyMatch { newer -> saved.id.equals(newer.id) }
        }
            .forEach {
                goodMapper.delete(it.id)
            }
    }

    override fun remove(message: Message) {
        // メッセージの論理削除
        messageMapper.delete(message.id)

        // いいねの論理削除
        goodMapper.selectByMessageId(message.id).forEach {
            goodMapper.delete(it.id)
        }
    }
}