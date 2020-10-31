package com.sample.board.api.application

import com.sample.board.api.common.exception.BusinessError
import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.repository.IGoodRepository
import com.sample.board.api.domain.repository.IMessageRepository
import com.sample.board.api.domain.repository.dto.MessageDto
import com.sample.board.api.domain.service.MessageDomainService
import com.sample.board.api.web.dto.PostGoodDto
import com.sample.board.api.web.dto.RemoveGoodDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * いいね！に関する操作を行う実装クラス
 */
@Service
class GoodService(
    private val messageRepository: IMessageRepository,
    private val goodRepository: IGoodRepository,
    private val domainService: MessageDomainService
) {

    /**
     * いいね！を登録する
     *
     * @param input いいね登録DTO
     */
    @Transactional
    fun postGood(input: PostGoodDto) {

        if (!domainService.isExist(input.messageId)) {
            throw BusinessError("いいね先が存在しません。")
        }

        val goodDtos = goodRepository.findByMessageId(input.messageId) ?: mutableListOf()
        val goods = goodDtos.map { dto -> Good(dto.id, dto.messageId, dto.userId, dto.isDeleted) }

        val messageDto: MessageDto = messageRepository.findById(input.messageId)!!
        val message = Message(
            messageDto.id,
            messageDto.type,
            messageDto.replyTo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goods.toMutableList()
        )

        val good = Good(
            input.messageId,
            input.userId
        )

        // いいねの登録
        message.addGood(good)
        messageRepository.save(message)
    }

    /**
     * いいね！を取り消す
     *
     * @param input いいね取消DTO
     */
    @Transactional
    fun removeGood(input: RemoveGoodDto) {

        if (!domainService.isExist(input.messageId)) {
            throw BusinessError("いいね先が存在しません。")
        }

        val goodDtoList = goodRepository.findByMessageId(input.messageId) ?: mutableListOf()
        val goods = goodDtoList.map { dto -> Good(dto.id, dto.messageId, dto.userId, dto.isDeleted) }

        val messageDto: MessageDto = messageRepository.findById(input.messageId)!!
        val message = Message(
            messageDto.id,
            messageDto.type,
            messageDto.replyTo,
            messageDto.userId,
            messageDto.comment,
            messageDto.isDeleted,
            goods.toMutableList()
        )

        // いいねの取り消し
        message.removeGood(input.userId)
        messageRepository.save(message)
    }
}