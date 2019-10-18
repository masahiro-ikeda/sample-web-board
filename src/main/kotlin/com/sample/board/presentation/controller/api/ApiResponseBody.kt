package com.sample.board.presentation.controller.api

import java.time.LocalDateTime

data class ApiResponseBody(
    val time: LocalDateTime,
    val message: String
)