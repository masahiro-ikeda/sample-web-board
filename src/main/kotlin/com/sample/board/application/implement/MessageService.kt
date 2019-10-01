package com.sample.board.application.implement

import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.application.message.MessageResources
import com.sample.board.domain.message.*
import com.sample.board.domain.user.LoginUser
import com.sample.board.query.IGoodQuery
import com.sample.board.query.IMessageQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    private val goodQuery: IGoodQuery,
    /** メッセージ内容の取得元 */
    private val errorMessage: MessageResources

) : IMessageService {

    /**
     * メッセージの新規投稿
     *
     * @param dto メッセージ投稿DTO
     */
    @Transactional
    override fun postMessage(dto: PostMessageDto) {

        // 新規メッセージモデルの生成
        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.MESSAGE.name,
            messageQuery.fetchMaxPostNo() + 1,
            0,
            dto.userId,
            dto.comment,
            0,
            mutableListOf<Good>()
        )
        // 永続化
        repository.store(message)
    }


    /**
     * 新規メッセージの投稿（返信）
     *
     * @param dto 返信メッセージ投稿DTO
     */
    @Transactional
    override fun postReply(dto: PostReplyDto) {

        // 有効な投稿Noかチェック
        messageQuery.fetchByPostNo(dto.postNo)
            ?: throw IllegalArgumentException(errorMessage.get("error.application.message.notExist"))

        // 新規メッセージモデル（返信）の生成
        val message = Message(
            UUID.randomUUID().toString(),
            MessageType.REPLY.name,
            dto.postNo,
            messageQuery.fetchMaxReplyNo(dto.postNo) + 1,
            dto.userId,
            dto.comment,
            0,
            mutableListOf()
        )
        // 永続化
        repository.store(message)
    }


    /**
     * 新規メッセージの消去
     *
     * @param messageId 削除対象メッセージID
     */
    @Transactional
    override fun deleteMessage(messageId: String, loginUser: LoginUser) {

        // メッセージモデルの生成
        val messageDto = messageQuery.fetchById(messageId)
            ?: throw IllegalArgumentException(errorMessage.get("error.application.message.notExist"))
        val goodList = goodQuery.fetchGoodById(messageId) ?: mutableListOf()
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

        // 自身のメッセージを削除しているかチェック
        if (!message.userId.equals(loginUser.username)) {
            throw IllegalArgumentException()
        }

        // メッセージ削除
        message.delete()
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
            val goods = allGood.stream().filter { good -> dto.id.equals(good.messageId) }.collect(Collectors.toList())
            val message = MessageForDisplay(
                dto.id,
                dto.type.name,
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