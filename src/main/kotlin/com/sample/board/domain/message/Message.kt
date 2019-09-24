package com.sample.board.domain.message

import com.sample.board.domain.user.LoginUser
import kotlin.streams.toList

class Message {
    val id: String
    val messageType: MessageType
    val postNo: Int
    val replyNo: Int
    val userId: String
    val comment: String
    var isDeleted: Int
    var goodList: MutableList<Good>

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
                .filter({ this.userId.equals(it.userId) })
                .toList()
                .isNotEmpty()
        )
            throw IllegalArgumentException()

        goodList.add(good)
    }

    fun getNumberOfGood(): Int {
        return goodList.count()
    }

    fun isAlreadyGood(loginUser: LoginUser): Boolean {
        return goodList.stream()
            .filter({ loginUser.username.equals(it.userId) })
            .toList()
            .isEmpty()
    }

    fun isReply(): Boolean {
        return this.messageType.equals(MessageType.REPLY)
    }
}