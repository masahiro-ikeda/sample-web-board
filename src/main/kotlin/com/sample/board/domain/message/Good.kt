package com.sample.board.domain.message

class Good {
    val id: String
    val messageId: String
    val userId: String
    private var isDeleted: Int

    constructor(id: String, messageId: String, userId: String, isDeleted: Int) {
        this.id = id
        this.messageId = messageId
        this.userId = userId
        this.isDeleted = isDeleted
    }

    fun delete() {
        isDeleted = 1
    }

    fun isDeleted(): Boolean {
        return isDeleted == 1
    }
}