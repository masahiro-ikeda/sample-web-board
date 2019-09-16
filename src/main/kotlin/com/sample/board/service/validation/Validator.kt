package com.sample.board.service.validation

import com.sample.board.service.exception.CreateUserException
import com.sample.board.service.message.ErrorMessageResource

class Validator(
    private val target: String?,
    private val errorMessage: ErrorMessageResource
) {
    // バリデーションチェック実施項目
    private var isCheckNotEmpty: Boolean = false
    private var isCheckSize: Boolean = false
    private var isCheckMatchPattern: Boolean = false

    // nullチェック
    fun checkNotEmpty(): Validator {
        isCheckNotEmpty = true
        return this
    }

    // 最大長チェック
    fun checkSize(maxSize: Int): Validator {
        isCheckSize = true
        this.maxSize = maxSize
        return this
    }

    // 正規表現チェック
    fun checkMatchPattern(pattern: String): Validator {
        isCheckMatchPattern = true
        this.pattern = pattern
        return this
    }

    /*
     * バリデーションチェック実行
     * nullチェックの最初に行うよう制御したいため
     */
    fun execute() {
        if (isCheckNotEmpty && isNotEmpty()) {
            throw CreateUserException(errorMessage.get("error.validation.null"))
        }
        if (isCheckSize && isSizeOver()) {
            throw CreateUserException(errorMessage.get("error.validation.sizeOver"))
        }
        if (isCheckMatchPattern && isMatchPattern()) {
            throw CreateUserException(errorMessage.get("error.validation.illegalPattern"))
        }
    }

    private fun isNotEmpty(): Boolean {
        return target.isNullOrEmpty()
    }

    private var maxSize: Int? = null
    private fun isSizeOver(): Boolean {
        return (!target.isNullOrEmpty() && target.length > maxSize!!)
    }

    private var pattern: String? = null
    private fun isMatchPattern(): Boolean {
        val regex = Regex(pattern = pattern!!)
        return (!target.isNullOrEmpty() && !regex.matches(target))
    }
}