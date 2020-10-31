package com.sample.board.api.web.presenter

import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.entity.message.MessageType
import java.time.LocalDateTime

/**
 * 表示用メッセージモデル
 */
class MessageForDisplay {
    val userName: String
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val message: Message

    constructor(
        id: String,
        messageType: MessageType,
        postNo: Int,
        replyNo: Int,
        userId: String,
        userName: String,
        comment: String,
        isDeleted: Int,
        goodList: MutableList<Good>,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ) {
        this.userName = userName
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.message = Message(
            id,
            messageType,
            postNo,
            replyNo,
            userId,
            comment,
            isDeleted,
            goodList
        )
    }
}