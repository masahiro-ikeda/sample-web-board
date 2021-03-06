package com.sample.board.api.web.controller

import com.sample.board.api.application.MessageService
import com.sample.board.api.common.security.LoginUser
import com.sample.board.api.web.dto.PostMessageDto
import com.sample.board.api.web.dto.PostReplyDto
import com.sample.board.api.web.presenter.MessagePresenter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * メッセージに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/message")
class MessageController(
    val service: MessageService,
    val presenter: MessagePresenter
) : ApiErrorHandler() {

    /**
     * メッセージの投稿を行う
     *
     * @param comment コメント
     */
    @PostMapping
    fun postMessage(
        @RequestParam(value = "_comment", required = false) comment: String?,
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
    @PostMapping("reply")
    fun postReply(
        @RequestParam(value = "messageId", required = true) messageId: Int,
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
        @PathVariable("messageId") messageId: Int?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId == null) throw IllegalArgumentException()
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