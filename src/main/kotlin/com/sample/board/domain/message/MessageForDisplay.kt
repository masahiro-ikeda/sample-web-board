package com.sample.board.domain.message

import java.time.LocalDateTime

/**
 * 表示用メッセージモデル
 */
class MessageForDisplay : Message {
    val userName: String
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime

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
    ) : super(
        id,
        messageType,
        postNo,
        replyNo,
        userId,
        comment,
        isDeleted,
        goodList
    ) {
        this.userName = userName
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}