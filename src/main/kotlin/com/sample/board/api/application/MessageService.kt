package com.sample.board.api.application

import com.sample.board.api.common.exception.BusinessError
import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.entity.message.MessageType
import com.sample.board.api.domain.query.IMessageQuery
import com.sample.board.api.domain.repository.IGoodRepository
import com.sample.board.api.domain.repository.IMessageRepository
import com.sample.board.api.domain.repository.dto.MessageDto
import com.sample.board.api.domain.service.MessageDomainService
import com.sample.board.api.web.dto.PostMessageDto
import com.sample.board.api.web.dto.PostReplyDto
import com.sample.board.api.web.presenter.MessageForDisplay
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 投稿メッセージに関する実装クラス
 */
@Service
class MessageService(
    private val messageRepository: IMessageRepository,
    private val goodRepository: IGoodRepository,
    private val domainService: MessageDomainService,
    private val messageQuery: IMessageQuery
) {

    /**
     * メッセージの新規投稿
     *
     * @param input メッセージ投稿DTO
     */
    @Transactional
    fun postMessage(input: PostMessageDto) {

        // 新規メッセージモデルの生成
        val newMessage = Message(
            MessageType.MESSAGE,
            null,
            input.userId,
            input.comment!!
        )

        // 永続化
        messageRepository.save(newMessage)
    }


    /**
     * 新規メッセージの投稿（返信）
     *
     * @param input 返信メッセージ投稿DTO
     */
    @Transactional
    fun postReply(input: PostReplyDto) {

        if (!domainService.isExist(input.messageId)) {
            throw BusinessError("返信先が存在しません。")
        }

        // 新規メッセージモデル（返信）の生成
        val newReply = Message(
            MessageType.REPLY,
            input.messageId,
            input.userId,
            input.comment!!
        )
        // 永続化
        messageRepository.save(newReply)
    }

    /**
     * メッセージの消去
     *
     * @param messageId 削除対象メッセージID
     */
    @Transactional
    fun deleteMessage(messageId: Int, loginUserId: String) {

        if (!domainService.isExist(messageId)) {
            throw BusinessError("いいね先が存在しません。")
        }

        val goodDtos = goodRepository.findByMessageId(messageId) ?: mutableListOf()
        val goods = goodDtos.map { dto -> Good(dto.id, dto.messageId, dto.userId, dto.isDeleted) }

        val messageDto: MessageDto = messageRepository.findById(messageId)!!
        val message = Message(
            messageDto.id,
            messageDto.type,
            messageDto.replyTo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goods.toMutableList()
        )

        message.delete(loginUserId)
        messageRepository.save(message)
    }

    /**
     * 画面表示用に投稿メッセージを全取得
     *
     */
    fun getMessages(): List<MessageForDisplay> {

        val allMessage = messageQuery.findDisplayMessage() ?: listOf()
        val allGood = goodRepository.findAll() ?: listOf()

        // DTOからモデルに変換
        val messageForDisplayList = mutableListOf<MessageForDisplay>()
        allMessage.forEach { dto ->

            // メッセージに紐づくいいねを抜き出す
            val goods = allGood
                .filter { good -> dto.id == good.messageId }
                .map { Good(it.id, it.messageId, it.userId, it.isDeleted) }

            // 表示用メッセージモデルの生成
            val message = MessageForDisplay(
                dto.id,
                MessageType.valueOf(dto.type),
                dto.replyTo,
                dto.userId,
                dto.userName,
                dto.comment,
                dto.isDeleted,
                goods,
                dto.UpdatedAt
            )
            messageForDisplayList.add(message)
        }

        return messageForDisplayList
    }
}
