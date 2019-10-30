package com.sample.board.presentation.controller.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.HttpClientErrorException
import java.time.LocalDateTime

/**
 * REST APIのエラーハンドリングを行うクラス
 *
 * @RestControllerAdviceを使うと通常のControllerの例外も拾うので
 * @RestControllerを付与したクラスに継承させて使う。
 */
open class ApiErrorHandler {

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
     *
     * 500系内部エラー
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleInternalError(): ResponseEntity<ApiResponseBody> {
        return ResponseEntity<ApiResponseBody>(
            ApiResponseBody(
                LocalDateTime.now(),
                "技術的な問題が発生しています。しばらく時間をおいた後に再送信して下さい。"
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}


