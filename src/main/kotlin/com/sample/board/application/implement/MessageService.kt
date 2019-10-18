package com.sample.board.application.implement

import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.*
import com.sample.board.query.IGoodQuery
import com.sample.board.query.IMessageQuery
import com.sample.board.query.dto.MessageDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.util.*
import java.util.stream.Collectors

/**
 * 投稿メッセージに関する実装クラス
 */
@Service
class MessageService(

    /** メッセージリポジトリ */
    private val repository: IMessageRepository,
    /** メッセージクエリ */
    private val messageQuery: IMessageQuery,
    /** いいねクエリ */
    private val goodQuery: IGoodQuery

) : IMessageService {

    /**
     * メッセージの新規投稿
     *
     * @param dto メッセージ投稿DTO
     */
    @Transactional
    override fun postMessage(input: PostMessageDto) {

        try {
            // 新規メッセージモデルの生成
            val newMessage = Message(
                UUID.randomUUID().toString(),
                MessageType.MESSAGE,
                messageQuery.fetchMaxPostNo() + 1,
                0,
                input.userId,
                input.comment,
                0,
                mutableListOf<Good>()
            )
            // 永続化
            repository.store(newMessage)

        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, e.message!!)
        }
    }


    /**
     * 新規メッセージの投稿（返信）
     *
     * @param dto 返信メッセージ投稿DTO
     */
    @Transactional
    override fun postReply(input: PostReplyDto) {

        // 返信対象のメッセージ情報の取得
        val replyTarget: MessageDto = messageQuery.fetchById(input.messageId)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "存在しないメッセージです。")

        try {
            // 新規メッセージモデル（返信）の生成
            val newReply = Message(
                UUID.randomUUID().toString(),
                MessageType.REPLY,
                replyTarget.postNo,
                messageQuery.fetchMaxReplyNo(replyTarget.postNo) + 1,
                input.userId,
                input.comment,
                0,
                mutableListOf()
            )
            // 永続化
            repository.store(newReply)

        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, e.message!!)
        }
    }

    /**
     * メッセージの消去
     *
     * @param messageId 削除対象メッセージID
     */
    @Transactional
    override fun deleteMessage(messageId: String, loginUserId: String) {

        // 削除対象のメッセージ情報の取得
        val deleteTarget: MessageDto = messageQuery.fetchById(messageId)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "存在しないメッセージです。")
        val targetGoodList = goodQuery.fetchGoodById(messageId) ?: mutableListOf()

        val message = Message(
            deleteTarget.id,
            deleteTarget.type,
            deleteTarget.postNo,
            deleteTarget.replyNo,
            deleteTarget.userId,
            deleteTarget.comment,
            deleteTarget.isDeleted,
            targetGoodList
        )

        // メッセージ削除処理
        if (!message.delete(loginUserId)) {
            throw throw HttpClientErrorException(HttpStatus.FORBIDDEN, "メッセージを削除する権限がありません。")
        }
        // 永続化
        repository.store(message)
    }


    /**
     * 画面表示用に投稿メッセージを全取得
     *
     */
    override fun getMessages(): List<MessageForDisplay> {

        val messageForDisplayList = mutableListOf<MessageForDisplay>()
        val allMessage = messageQuery.fetchAll() ?: listOf()
        val allGood = goodQuery.fetchAllGood() ?: listOf()

        // DTOからモデルに変換
        allMessage.forEach { dto ->

            // メッセージに紐づくいいねを抜き出す
            val goods = allGood.stream().filter { good ->
                (dto.id == good.messageId)
            }.collect(Collectors.toList())

            // 表示用メッセージモデルの生成
            val message = MessageForDisplay(
                dto.id,
                dto.type,
                dto.postNo,
                dto.replyNo,
                dto.userId,
                dto.userName,
                dto.comment,
                dto.isDeleted,
                goods,
                dto.createdAt,
                dto.updatedAt
            )

            messageForDisplayList.add(message)
        }

        return messageForDisplayList
    }
}