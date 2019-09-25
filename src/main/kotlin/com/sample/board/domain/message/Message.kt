package com.sample.board.domain.message

import kotlin.streams.toList

open class Message {
    val id: String
    val messageType: MessageType
    val postNo: Int
    val replyNo: Int
    val userId: String
    val comment: String
    private var isDeleted: Int
    private var goodList: MutableList<Good>

    constructor(
        id: String,
        messageType: String,
        postNo: Int,
        replyNo: Int,
        userId: String,
        comment: String,
        isDeleted: Int,
        goodList: MutableList<Good>
    ) {
        this.id = id
        this.messageType = MessageType.valueOf(messageType)
        this.postNo = postNo
        this.replyNo = replyNo
        this.userId = userId
        this.comment = comment
        this.isDeleted = isDeleted
        this.goodList = goodList
    }

    fun addGood(good: Good) {
        // メッセージIDの照合
        if (!id.equals(good.messageId)) throw IllegalArgumentException()

        // 同一ユーザが2回いいねしてないかチェック
        if (goodList.stream()
                .filter({ good.userId.equals(it.userId) })
                .toList()
                .isNotEmpty()
        )
            throw IllegalArgumentException()

        goodList.add(good)
    }

    fun removeGood(targetUserId: String) {

        // いいねが1件もないときはエラー
        if (goodList.size == 0) throw IllegalArgumentException()

        // いいねの削除フラグを立てる
        goodList.stream().filter { targetUserId.equals(it.userId) }.forEach {
            it.delete()
        }
    }

    fun getGoodList(): List<Good> {
        return goodList
    }

    fun getNumberOfGood(): Int {
        return goodList.count()
    }

    fun isAlreadyGood(userId: String): Boolean {
        return goodList.stream()
            .filter({ userId.equals(it.userId) })
            .toList()
            .isEmpty()
    }

    fun isReply(): Boolean {
        return this.messageType.equals(MessageType.REPLY)
    }

    fun delete() {
        this.isDeleted = 1
        goodList.forEach { it.delete() }
    }

    fun isDeleted(): Boolean {
        return this.isDeleted == 1
    }
}