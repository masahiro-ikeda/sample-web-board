package com.sample.board.api.domain.entity.message

import com.sample.board.api.common.exception.BusinessError
import kotlin.streams.toList

/**
 * 投稿メッセージモデル
 */
class Message {

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
    /* コメント */
    val comment: String?
    /* 削除フラグ */
    private var isDeleted: Int
    /* いいね一覧 */
    private var goodList: MutableList<Good>

    // 定数設定
    companion object {
        const val MAX_LENGTH_OF_COMMENT: Int = 100
    }

    /**
     * 新規作成時のコンストラクタ.
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
        userId: String,
        comment: String?,
        isDeleted: Int,
        goodList: MutableList<Good>
    ) {
        // コメントの字数チェック
        if (comment != null && comment.length > MAX_LENGTH_OF_COMMENT) {
            throw BusinessError("メッセージは${MAX_LENGTH_OF_COMMENT}文字以下で入力して下さい。")
        }

        this.id = id
        this.messageType = messageType
        this.postNo = postNo
        this.replyNo = replyNo
        this.userId = userId
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
        if (this.userId != loginUserId) {
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