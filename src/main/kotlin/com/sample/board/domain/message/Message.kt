package com.sample.board.domain.message

class Message {
    val id: String
    val messageType: MessageType
    val postNo: Int
    val replyNo: Int
    val userId: String
    val content: String
    var isDeleted: Int

    constructor(
        id: String,
        messageType: String,
        postNo: Int,
        replyNo: Int,
        userId: String,
        content: String,
        isDeleted: Int
    ) {
        this.id = id
        this.messageType = MessageType.valueOf(messageType)
        this.postNo = postNo
        this.replyNo = replyNo
        this.userId = userId
        this.content = content
        this.isDeleted = isDeleted
    }
}