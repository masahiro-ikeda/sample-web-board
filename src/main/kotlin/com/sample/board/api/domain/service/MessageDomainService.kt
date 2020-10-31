package com.sample.board.api.domain.service

import com.sample.board.api.domain.repository.IMessageRepository
import org.springframework.stereotype.Component

@Component
class MessageDomainService(
    private val messageRepository: IMessageRepository
) {

    /**
     * メッセージが存在するか？
     */
    fun isExist(id: Int): Boolean {
        return messageRepository.findById(id) != null
    }
}