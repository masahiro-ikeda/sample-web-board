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

        // DBにない場合は新規作成
        if (messageMapper.selectById(message.id).isNullOrEmpty()) {
            messageMapper.insert(message)
        }
        // 削除
        if (message.isDeleted()) {
            messageMapper.delete(message.id)
        }

        val oldGoods = goodMapper.selectByMessageId(message.id)
        val newGoods = message.getGoodList()

        // いいねの追加を反映
        newGoods.stream().filter {
            !oldGoods.stream().anyMatch { older -> it.id.equals(older.id) }
        }.forEach { newer ->
            goodMapper.insert(newer)
        }

        // いいねの削除
        newGoods.forEach {
            if (it.isDeleted()) goodMapper.delete(it.id)
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