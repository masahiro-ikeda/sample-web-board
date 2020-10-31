package com.sample.board.api.application

import com.sample.board.api.web.dto.PostMessageDto
import com.sample.board.api.web.dto.PostReplyDto
import com.sample.board.api.domain.entity.message.*
import com.sample.board.api.domain.query.IGoodQuery
import com.sample.board.api.domain.query.IMessageQuery
import com.sample.board.api.domain.query.dto.MessageDto
import com.sample.board.api.domain.repository.IMessageRepository
import com.sample.board.api.web.presenter.MessageForDisplay
import org.slf4j.LoggerFactory
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

) {

    // ロガー
    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    /**
     * メッセージの新規投稿
     *
     * @param dto メッセージ投稿DTO
     */
    @Transactional
    fun postMessage(input: PostMessageDto) {

        logger.info("start: postMessage")

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
            repository.save(newMessage)

        } catch (e: IllegalArgumentException) {
            logger.error(e.message!!)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, e.message!!)
        } catch (e: Exception) {
            logger.error(e.message!!)
        }

        logger.info("finish: postMessage")
    }


    /**
     * 新規メッセージの投稿（返信）
     *
     * @param dto 返信メッセージ投稿DTO
     */
    @Transactional
    fun postReply(input: PostReplyDto) {

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
            repository.save(newReply)

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
    fun deleteMessage(messageId: String, loginUserId: String) {

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
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "メッセージを削除する権限がありません。")
        }
        // 永続化
        repository.save(message)
    }


    /**
     * 画面表示用に投稿メッセージを全取得
     *
     */
    fun getMessages(): List<MessageForDisplay> {

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