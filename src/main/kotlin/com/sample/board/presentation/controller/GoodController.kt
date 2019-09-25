package com.sample.board.presentation.controller

import com.sample.board.application.GoodService
import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.RemoveGoodDto
import com.sample.board.domain.user.LoginUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * いいねに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/good")
class GoodController(
    val service: GoodService
) {

    @PostMapping("poster")
    fun postGood(
        @RequestParam("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()

        val dto = PostGoodDto(messageId, loginUser.username)
        service.postGood(dto)
    }

    @PostMapping("remover")
    fun removeGood(
        @RequestParam("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()

        val dto = RemoveGoodDto(messageId, loginUser.username)
        service.removeGood(dto)
    }
}