package com.sample.board.api.web.controller

import java.time.LocalDateTime

data class ApiResponseBody(
    val time: LocalDateTime,
    val message: String
)