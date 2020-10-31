package com.sample.board.api.application

import com.sample.board.api.web.dto.PostGoodDto
import com.sample.board.api.web.dto.RemoveGoodDto
import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.repository.IMessageRepository
import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.query.IGoodQuery
import com.sample.board.api.domain.query.IMessageQuery
import com.sample.board.api.domain.query.dto.MessageDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.util.*

/**
 * いいね！に関する操作を行う実装クラス
 */
@Service
class GoodService(

    /** メッセージリポジトリ */
    private val repository: IMessageRepository,
    /** メッセージクエリ */
    private val messageQuery: IMessageQuery,
    /** いいねクエリ */
    private val goodQuery: IGoodQuery

) {

    /**
     * いいね！を登録する
     *
     * @param dto いいね登録DTO
     */
    @Transactional
    fun postGood(dto: PostGoodDto) {

        // いいね対象のメッセージ情報の取得
        val goodTarget: MessageDto = messageQuery.fetchById(dto.messageId)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "存在しないメッセージです。")
        val targetGoodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()

        val message = Message(
            goodTarget.id,
            goodTarget.type,
            goodTarget.postNo,
            goodTarget.replyNo,
            goodTarget.userId,
            goodTarget.comment,
            goodTarget.isDeleted,
            targetGoodList
        )

        val newGood = Good(
            UUID.randomUUID().toString(),
            dto.messageId,
            dto.userId,
            0
        )

        // いいねの登録
        if (!message.addGood(newGood)) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "同一メッセージに付与できるいいねは1回のみです。")
        }

        // 永続化
        repository.save(message)
    }

    /**
     * いいね！を取り消す
     *
     * @param dto いいね取消DTO
     */
    @Transactional
    fun removeGood(dto: RemoveGoodDto) {

        // 取消対象いいねのメッセージ情報の取得
        val goodTarget: MessageDto = messageQuery.fetchById(dto.messageId)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "存在しないメッセージです。")
        val targetGoodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()

        val message = Message(
            goodTarget.id,
            goodTarget.type,
            goodTarget.postNo,
            goodTarget.replyNo,
            goodTarget.userId,
            goodTarget.comment,
            goodTarget.isDeleted,
            targetGoodList
        )

        // いいねのキャンセル
        if (!message.removeGood(dto.userId)) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "いいねが存在しません。")
        }
        // 永続化
        repository.save(message)
    }
}