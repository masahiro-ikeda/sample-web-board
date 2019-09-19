package com.sample.board.web.controller.board

import com.google.gson.Gson
import com.sample.board.domain.model.LoginUser
import com.sample.board.domain.model.enumeration.MessageType
import com.sample.board.domain.service.IPostService
import com.sample.board.domain.service.dto.MessageCreateDto
import com.sample.board.web.form.CreatePostMessageForm
import com.sample.board.web.form.CreateReplyMessageForm
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    val service: IPostService,
    val validator: SmartValidator
) {
    @PostMapping("poster/post")
    fun createPostMessage(
        @ModelAttribute("CreatePostMessageForm") input: CreatePostMessageForm,
        bindingResult: BindingResult, @AuthenticationPrincipal userDetails: LoginUser
    ): String {

        // 共通チェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) {
            return "failure"
        }

        val dto = MessageCreateDto(
            MessageType.POST,
            0,
            userDetails.username,
            input.content!!
        )
        service.createMessage(dto)
        return "success"
    }

    @PostMapping("poster/reply")
    fun createReplyMessage(
        @ModelAttribute("CreateReplyMessageForm") input: CreateReplyMessageForm,
        bindingResult: BindingResult, @AuthenticationPrincipal userDetails: LoginUser
    ): String {

        // 共通チェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) {
            return "failure"
        }

        val dto = MessageCreateDto(
            MessageType.REPLY,
            input.postNo!!.toInt(),
            userDetails.username,
            input.content!!
        )
        service.createMessage(dto)
        return "success"
    }

    @GetMapping("post")
    fun readMessages(
        @AuthenticationPrincipal userDetails: LoginUser
    ): String {
        val messages = service.fetchMessage()
        return Gson().toJson(messages)
    }
}