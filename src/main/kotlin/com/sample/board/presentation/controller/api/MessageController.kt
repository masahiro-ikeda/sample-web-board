package com.sample.board.presentation.controller.api

import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageType
import com.sample.board.domain.user.LoginUser
import com.sample.board.presentation.form.IReplyCheck
import com.sample.board.presentation.form.PostMessageForm
import com.sample.board.presentation.presenter.MessagePresenter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.*

/**
 * メッセージに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/message")
class MessageController(
    val service: IMessageService,
    val validator: SmartValidator,
    val presenter: MessagePresenter
) {

    /**
     * メッセージの投稿を行う
     *
     * @param input 入力値
     * @param bindingResult バリデーション結果
     * @param loginUser ログインユーザ
     */
    @PostMapping
    fun postMessage(
        @ModelAttribute("PostMessageForm") input: PostMessageForm,
        bindingResult: BindingResult, @AuthenticationPrincipal loginUser: LoginUser
    ) {

        // 共通チェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) throw IllegalArgumentException()

        when {
            // メッセージの投稿
            input.type.equals(MessageType.MESSAGE.name) -> {
                val dto = PostMessageDto(
                    loginUser.username,
                    input.comment!!
                )
                service.postMessage(dto)
            }

            // リプライの投稿
            input.type.equals(MessageType.REPLY.name) -> {
                // 返信パラメータ用チェック
                validator.validate(input, bindingResult, IReplyCheck::class)
                if (bindingResult.hasErrors()) throw IllegalArgumentException()

                val dto = PostReplyDto(
                    input.postNo!!.toInt(),
                    loginUser.username,
                    input.comment!!
                )
                service.postReply(dto)
            }
        }
    }

    /**
     * メッセージの削除を行う
     *
     * @param messageId メッセージID
     * @param loginUser ログインユーザ
     */
    @DeleteMapping("/{messageId}")
    fun deleteMessage(
        @PathVariable("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()
        service.deleteMessage(messageId, loginUser)
    }

    /**
     * メッセージの取得を行う
     *
     * @param loginUser ログインユーザ
     */
    @GetMapping
    fun getMessages(@AuthenticationPrincipal loginUser: LoginUser): String {
        val messages = service.getMessages()
        return presenter.formatToHtml(messages, loginUser)
    }
}