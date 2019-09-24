package com.sample.board.presentation.controller

import com.google.gson.Gson
import com.sample.board.application.IMessageService
import com.sample.board.application.dto.PostMessageDto
import com.sample.board.application.dto.PostReplyDto
import com.sample.board.domain.user.LoginUser
import com.sample.board.presentation.form.PostMessageForm
import com.sample.board.presentation.form.PostReplyForm
import com.sample.board.presentation.presenter.PostPresenter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    val service: IMessageService,
    val validator: SmartValidator,
    val presenter: PostPresenter
) {
    @PostMapping("poster/message")
    fun createPostMessage(
        @ModelAttribute("PostMessageForm") input: PostMessageForm,
        bindingResult: BindingResult, @AuthenticationPrincipal loginUser: LoginUser
    ): String {

        // 共通チェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) {
            return "failure"
        }

        val dto = PostMessageDto(
            loginUser.username,
            input.content!!
        )
        service.postMessage(dto)
        return "success"
    }

    @PostMapping("poster/reply")
    fun createReplyMessage(
        @ModelAttribute("PostReplyForm") input: PostReplyForm,
        bindingResult: BindingResult, @AuthenticationPrincipal userDetails: LoginUser
    ): String {

        // 共通チェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) {
            return "failure"
        }

        val dto = PostReplyDto(
            input.postNo!!.toInt(),
            userDetails.username,
            input.content!!
        )
        service.postReply(dto)
        return "success"
    }

    @GetMapping("messages")
    fun readMessages(
        @AuthenticationPrincipal userDetails: LoginUser
    ): String {
        val messages = service.fetchMessages()
        return presenter.formatToHtml(messages)
    }
}