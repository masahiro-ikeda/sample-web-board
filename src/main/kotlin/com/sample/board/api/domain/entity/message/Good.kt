package com.sample.board.api.domain.entity.message

class Good {
    private val id: Int?
    private val messageId: Int
    private val userId: String
    private var isDeleted: Int

    /**
     * 新規作成時.
     */
    constructor(messageId: Int, userId: String) {
        this.id = null
        this.messageId = messageId
        this.userId = userId
        this.isDeleted = 0
    }

    /**
     * データ読み込み時.
     */
    constructor(id: Int, messageId: Int, userId: String, isDeleted: Int) {
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

    // 以下Getter
    fun getId(): Int? {
        return this.id
    }

    fun getMessageId(): Int {
        return this.messageId
    }

    fun getUserId(): String {
        return this.userId
    }

    fun getIsDeleted(): Int {
        return isDeleted
    }
}
