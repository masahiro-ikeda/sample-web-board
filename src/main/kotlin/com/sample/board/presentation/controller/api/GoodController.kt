package com.sample.board.presentation.controller.api

import com.sample.board.application.implement.GoodService
import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.RemoveGoodDto
import com.sample.board.domain.user.LoginUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * いいねに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/good/{messageId}")
class GoodController(
    val service: GoodService
) {

    @PostMapping
    fun postGood(
        @PathVariable("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()

        val dto = PostGoodDto(messageId, loginUser.username)
        service.postGood(dto)
    }

    @DeleteMapping
    fun deleteGood(
        @PathVariable("messageId") messageId: String?,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
        if (messageId.isNullOrEmpty()) throw IllegalArgumentException()

        val dto = RemoveGoodDto(messageId, loginUser.username)
        service.removeGood(dto)
    }
}