package com.sample.board.application.exception

import org.springframework.validation.FieldError
import kotlin.streams.toList

/**
 * 入力フォームのバリテーションチェック不正で発生した例外
 */
class FormValidateException(val errors: MutableList<FieldError>) : Exception() {

    fun getErrorMessageList(): List<String> {
        return errors.stream().map {
            "field: ${it.field}, value: ${it.rejectedValue}, message: ${it.defaultMessage}"
        }.toList()
    }
}