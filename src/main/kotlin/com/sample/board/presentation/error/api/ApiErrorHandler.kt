package com.sample.board.presentation.error.api

import com.sample.board.application.exception.FormValidateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class ApiErrorHandler {

    /**
     * クエリパラメータの形式が不正だった場合の例外処理
     */
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun handleIllegalArgumentError(e: IllegalArgumentException): ResponseEntity<ApiResponseBody> {
        return ResponseEntity<ApiResponseBody>(
            ApiResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "error",
                listOf(e.message!!)
            ), HttpStatus.BAD_REQUEST
        )
    }

    /**
     * バリデーションチェック不正が発生した場合
     */
    @ExceptionHandler(FormValidateException::class)
    @ResponseBody
    fun handleFormValidateError(e: FormValidateException): ResponseEntity<ApiResponseBody> {
        return ResponseEntity<ApiResponseBody>(
            ApiResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "error",
                e.getErrorMessageList()
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handleInternalError(e: RuntimeException): String {
        return "通信エラーが発生したため再読込して下さい。"
    }
}


