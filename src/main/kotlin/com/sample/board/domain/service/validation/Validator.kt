package com.sample.board.domain.service.validation

import com.sample.board.domain.service.exception.ValidationErrorException
import com.sample.board.domain.service.message.ErrorMessageResource

class Validator(
    private val targetValue: String?,
    private val errorMessage: ErrorMessageResource
) {

    // nullチェック
    fun checkNotEmpty(): Validator {

        if (targetValue.isNullOrEmpty()) {
            throw ValidationErrorException(errorMessage.get("error.validation.null"))
        }
        return this
    }

    // 最大長チェック
    fun checkSize(maxSize: Int): Validator {

        if (!targetValue.isNullOrEmpty() && targetValue.length > maxSize){
            throw ValidationErrorException(errorMessage.get("error.validation.sizeOver"))
        }
        return this
    }

    // 正規表現チェック
    fun checkMatchPattern(pattern: String): Validator {

        val regex = Regex(pattern = pattern)
        if (!targetValue.isNullOrEmpty() && !regex.matches(targetValue)){
            throw ValidationErrorException(errorMessage.get("error.validation.illegalPattern"))
        }
        return this
    }
}