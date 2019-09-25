package com.sample.board.presentation.controller

import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.message.MessageType
import com.sample.board.domain.user.LoginUser
import com.sample.board.presentation.form.IReplyCheck
import com.sample.board.presentation.form.PostMessageForm
import com.sample.board.presentation.presenter.PostPresenter
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
    val presenter: PostPresenter
) {

    /**
     * メッセージの投稿を行う
     *
     * @param input 入力値
     * @param bindingResult バリデーション結果
     * @param loginUser ログインユーザ
     */
    @PostMapping("poster")
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

    @GetMapping
    fun readMessages(
        @AuthenticationPrincipal loginUser: LoginUser
    ): String {
        val messages = service.fetchMessages()
        return presenter.formatToHtml(messages, loginUser)
    }
}