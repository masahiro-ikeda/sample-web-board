package com.sample.board.presentation.controller.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import java.time.LocalDateTime

@RestControllerAdvice
class ApiErrorHandler {

    /**
     * 400系クライアントエラー
     */
    @ExceptionHandler(HttpClientErrorException::class)
    @ResponseBody
    fun handleClientError(e: HttpClientErrorException): ResponseEntity<ApiResponseBody> {
        return ResponseEntity<ApiResponseBody>(
            ApiResponseBody(
                LocalDateTime.now(),
                e.message!!
            ), e.statusCode
        )
    }

    /**
     * 500系内部エラー
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleInternalError(e: Exception): ResponseEntity<ApiResponseBody> {
        return ResponseEntity<ApiResponseBody>(
            ApiResponseBody(
                LocalDateTime.now(),
                "通信エラーが発生しました。しばらく時間をおいた後に再送信して下さい。"
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}


