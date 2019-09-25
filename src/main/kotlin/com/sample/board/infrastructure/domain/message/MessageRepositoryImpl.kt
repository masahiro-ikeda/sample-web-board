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

        val savedList = goodMapper.selectByMessageId(message.id)
        val newerList = message.getGoodList()

        // いいねの追加を反映
        newerList.stream().filter { newer ->
            !savedList.stream().anyMatch { saved -> newer.id.equals(saved.id) }
        }
            .forEach {
                goodMapper.insert(it)
            }

        // いいねの削除
        newerList.forEach {
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