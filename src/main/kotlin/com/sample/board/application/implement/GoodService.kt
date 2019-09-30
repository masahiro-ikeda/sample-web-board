package com.sample.board.application.implement

import com.sample.board.application.IGoodService
import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.CancelGoodDto
import com.sample.board.application.message.MessageResources
import com.sample.board.domain.message.Good
import com.sample.board.domain.message.IMessageRepository
import com.sample.board.domain.message.Message
import com.sample.board.query.IGoodQuery
import com.sample.board.query.IMessageQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

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
    private val goodQuery: IGoodQuery,
    /** メッセージ内容の取得元 */
    private val errorMessage: MessageResources

) : IGoodService {

    /**
     * いいね！を登録する
     *
     * @param dto いいね登録DTO
     */
    @Transactional
    override fun postGood(dto: PostGoodDto) {

        // メッセージモデルの生成
        val messageDto = messageQuery.fetchById(dto.messageId)
            ?: throw IllegalArgumentException(errorMessage.get("error.application.message.notExist"))
        val goodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()
        val message = Message(
            messageDto.id,
            messageDto.type.name,
            messageDto.postNo,
            messageDto.replyNo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goodList
        )

        // いいねの登録
        message.addGood(
            Good(UUID.randomUUID().toString(), dto.messageId, dto.userId, 0)
        )

        // 永続化
        repository.store(message)
    }

    /**
     * いいね！を取り消す
     *
     * @param dto いいね取消DTO
     */
    @Transactional
    override fun cancelGood(dto: CancelGoodDto) {

        // メッセージモデルの生成
        val messageDto = messageQuery.fetchById(dto.messageId)
            ?: throw IllegalArgumentException(errorMessage.get("error.application.message.notExist"))
        val goodList = goodQuery.fetchGoodById(dto.messageId) ?: mutableListOf()
        val message = Message(
            messageDto.id,
            messageDto.type.name,
            messageDto.postNo,
            messageDto.replyNo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goodList
        )

        // いいねのキャンセル
        message.cancelGood(dto.userId)

        // 永続化
        repository.store(message)
    }
}