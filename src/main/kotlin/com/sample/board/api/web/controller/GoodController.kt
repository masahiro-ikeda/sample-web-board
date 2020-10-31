package com.sample.board.api.web.controller

import com.sample.board.api.web.dto.PostGoodDto
import com.sample.board.api.web.dto.RemoveGoodDto
import com.sample.board.api.application.GoodService
import com.sample.board.api.common.security.LoginUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * いいねに関する処理を扱うコントローラ
 */
@RestController
@RequestMapping("api/good/{messageId}")
class GoodController(
    val service: GoodService
) : ApiErrorHandler() {

    @PostMapping
    fun postGood(
        @PathVariable("messageId") messageId: String,
        @AuthenticationPrincipal loginUser: LoginUser
    ) {
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