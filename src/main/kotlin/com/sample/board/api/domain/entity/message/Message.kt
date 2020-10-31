package com.sample.board.api.domain.entity.message

import com.sample.board.api.common.exception.BusinessError
import kotlin.streams.toList

/**
 * 投稿メッセージモデル
 */
class Message {

    /* メッセージID */
    private val id: Int?

    /* メッセージ種別 */
    private val messageType: MessageType

    /* 返信先 */
    private val replyTo: Int?

    /* 投稿ユーザID */
    private val userId: String

    /* コメント */
    private val comment: String

    /* 削除フラグ */
    private var isDeleted: Int

    /* いいね一覧 */
    private var goodList: MutableList<Good>

    // 定数
    companion object {
        const val MAX_LENGTH_OF_COMMENT: Int = 100
    }

    /**
     * 新規作成時.
     */
    constructor(
        messageType: MessageType,
        replyTo: Int?,
        userId: String,
        comment: String
    ) {
        if (comment.length > MAX_LENGTH_OF_COMMENT) {
            throw BusinessError("メッセージは${MAX_LENGTH_OF_COMMENT}文字以下で入力して下さい。")
        }
        this.id = null
        this.messageType = messageType
        if (this.messageType.equals(MessageType.REPLY)) {
            this.replyTo = replyTo ?: throw BusinessError("返信先が指定されていません。")
        } else {
            this.replyTo = replyTo
        }
        this.userId = userId
        this.comment = comment
        this.isDeleted = 0
        this.goodList = mutableListOf()
    }

    /**
     * 読み込み時.
     */
    constructor(
        id: Int,
        messageType: String,
        replyTo: Int?,
        userId: String,
        comment: String,
        isDeleted: Int,
        goodList: MutableList<Good>
    ) {
        this.id = id
        this.messageType = MessageType.valueOf(messageType)
        this.replyTo = replyTo
        this.userId = userId
        this.comment = comment
        this.isDeleted = isDeleted
        this.goodList = goodList
    }

    /**
     * 投稿メッセージの削除
     *
     * @param loginUserId ログイン中のユーザID
     */
    fun delete(loginUserId: String) {

        // ユーザは自分の投稿しか削除できない
        if (this.userId != loginUserId) {
            throw BusinessError("メッセージが存在しません。")
        }

        // 削除実施
        this.isDeleted = 1
        goodList.forEach { it.delete() }
    }

    /**
     * いいね付与
     *
     * @param good 追加いいね
     *
     * @return いいね付与結果
     */
    fun addGood(good: Good) {

        // 同一ユーザーは2回いいねできない
        if (goodList.stream()
                .filter({ good.getUserId() == it.getUserId() })
                .toList()
                .isNotEmpty()
        ) {
            throw BusinessError("いいねが押せるのは1回だけです。")
        }

        goodList.add(good)
    }

    /**
     * いいね取り消し
     *
     * @param userId いいねを取り消すユーザーID
     *
     * @return いいね取り消し結果
     */
    fun removeGood(userId: String) {

        // いいねの存在チェック
        if (goodList.stream()
                .filter({ good -> userId.equals(good.getUserId()) })
                .toList()
                .isEmpty()
        ) {
            throw BusinessError("いいねがありません。")
        }

        // 削除
        goodList.stream().forEach { good ->
            if (userId.equals(good.getUserId())) {
                good.delete()
            }
        }
    }

    fun isDeleted(): Boolean {
        return this.isDeleted == 1
    }

    // 以下、Getter
    fun getId(): Int? {
        return this.id
    }

    fun getMessageType(): MessageType {
        return this.messageType
    }

    fun getReplyTo(): Int? {
        return this.replyTo
    }

    fun getUserId(): String {
        return this.userId
    }

    fun getComment(): String {
        return this.comment
    }

    fun getGoods(): List<Good> {
        return this.goodList
    }
}
