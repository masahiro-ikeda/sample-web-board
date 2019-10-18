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
    val postUserId: String
    /* 投稿内容 */
    val comment: String
    /* 削除フラグ */
    private var isDeleted: Int
    /* いいね一覧 */
    private var goodList: MutableList<Good>

    companion object {
        const val MAX_LENGTH_OF_COMMENT: Int = 100
    }

    /**
     * コンストラクタ
     * 外部から入力される可能性のあるcommentは入力チェックを実施
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
        messageType: MessageType,
        postNo: Int,
        replyNo: Int,
        postUserId: String,
        comment: String?,
        isDeleted: Int,
        goodList: MutableList<Good>
    ) {
        this.id = id
        this.messageType = messageType
        this.postNo = postNo
        this.replyNo = replyNo
        this.postUserId = postUserId
        // 投稿メッセージの入力チェック
        if (comment.isNullOrBlank()) {
            throw IllegalArgumentException("メッセージを入力して下さい。")
        }
        if (MAX_LENGTH_OF_COMMENT < comment.length) {
            throw IllegalArgumentException("メッセージは${MAX_LENGTH_OF_COMMENT}文字以下で入力して下さい。")
        }
        this.comment = comment
        this.isDeleted = isDeleted
        this.goodList = goodList
    }

    /**
     * 投稿メッセージの削除
     *
     * @param loginUserId ログイン中のユーザID
     *
     * @return 削除処理の結果
     */
    fun delete(loginUserId: String): Boolean {

        // ユーザは自分の投稿しか削除できない
        if (this.postUserId != loginUserId) {
            return false
        }

        // 削除実施
        this.isDeleted = 1
        goodList.forEach { it.delete() }
        return true
    }

    /**
     * いいね付与
     *
     * @param good 追加いいね
     *
     * @return いいね付与結果
     */
    fun addGood(newGood: Good): Boolean {

        // 同一ユーザーは2回いいねできない
        if (goodList.stream()
                .filter({ newGood.userId.equals(it.userId) })
                .toList()
                .isNotEmpty()
        ) {
            return false
        }

        goodList.add(newGood)
        return true
    }

    /**
     * いいね取り消し
     *
     * @param targetUserId いいねを取り消すユーザーID
     *
     * @return いいね取り消し結果
     */
    fun removeGood(targetUserId: String): Boolean {

        // 取り消し対象のいいねが存在しない
        if (goodList.stream()
                .filter({ good -> targetUserId.equals(good.userId) })
                .toList()
                .isEmpty()
        ) {
            return false
        }

        // いいねの削除実施
        goodList.stream().filter { good ->
            targetUserId.equals(good.userId)
        }.forEach { removeTarget ->
            removeTarget.delete()
        }
        return true
    }

    fun getGoodList(): List<Good> {
        return goodList
    }

    fun getNumberOfGood(): Int {
        return goodList.count()
    }

    fun isAlreadyGood(userId: String): Boolean {
        return !goodList.stream()
            .filter({ userId.equals(it.userId) })
            .toList()
            .isEmpty()
    }

    fun isReply(): Boolean {
        return this.messageType.equals(MessageType.REPLY)
    }

    fun isDeleted(): Boolean {
        return this.isDeleted == 1
    }
}