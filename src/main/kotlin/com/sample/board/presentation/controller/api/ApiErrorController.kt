package com.sample.board.presentation.controller.api

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

@RestControllerAdvice
class ApiErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun handleIllegalArgumentError(e: IllegalArgumentException): String {
        return e.message!!
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handleInternalError(e: RuntimeException): String {
        return "通信エラーが発生したため再読込して下さい。"
    }
}
