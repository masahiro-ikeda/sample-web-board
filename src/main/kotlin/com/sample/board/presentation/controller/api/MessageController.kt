package com.sample.board.presentation.controller.api

import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.user.LoginUser
import com.sample.board.presentation.presenter.MessagePresenter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * メッセージに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/message")
class MessageController(
    val service: IMessageService,
    val presenter: MessagePresenter
) {

    /**
     * メッセージの投稿を行う
     *
     * @param comment コメント
     */
    @PostMapping
    fun postMessage(
        @RequestParam(value = "comment", required = false) comment: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        val dto = PostMessageDto(
            loginUser.username,
            comment
        )
        service.postMessage(dto)
    }

    /**
     * メッセージへの返信を行う
     *
     * @param messageId 返信対象のメッセージID
     * @param comment コメント
     */
    @PostMapping("{messageId}")
    fun postReply(
        @PathVariable("messageId") messageId: String,
        @RequestParam(value = "comment", required = false) comment: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        val dto = PostReplyDto(
            loginUser.username,
            messageId,
            comment
        )
        service.postReply(dto)
    }

    /**
     * メッセージの削除を行う
     *
     * @param messageId メッセージID
     * @param loginUser ログインユーザ
     */
    @DeleteMapping("{messageId}")
    fun deleteMessage(
        @PathVariable("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()
        service.deleteMessage(messageId, loginUser.username)
    }

    /**
     * メッセージの取得を行う
     *
     * @param loginUser ログインユーザ
     */
    @GetMapping
    fun getMessages(@AuthenticationPrincipal loginUser: LoginUser): String {
        val messages = service.getMessages()
        return presenter.formatToHtml(messages, loginUser.username)
    }
}