package com.sample.board.presentation.error.api

import java.time.LocalDateTime

data class ApiResponseBody(
    val code: Int,
    val time: LocalDateTime,
    val message: String,
    val errors: List<String>?
)