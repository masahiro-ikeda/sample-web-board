package com.sample.board.domain.message

import kotlin.streams.toList

/**
 * 投稿メッセージモデル
 */
open class Message {

    /* メッセージID */
    val id: String
    /* メッセージ種別 */
    val messageType: MessageType
    /* 投稿No */
    val postNo: Int
    /* 返信No */
    val replyNo: Int
    /* 投稿ユーザID */
    val userId: String
    /* 投稿内容 */
    val comment: String
    /* 削除フラグ */
    private var isDeleted: Int
    /* いいね一覧 */
    private var goodList: MutableList<Good>

    /**
     * コンストラクタ
     *
     * @param id
     * @param messageType
     * @param postNo
     * @param replyNo
     * @param userId
     * @param comment
     * @param isDeleted
     * @param goodList
     */
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