package com.sample.board.presentation.controller.api

class ApiValidator {

    // nullチェック
    fun checkNotEmpty(field: String, value: String?) {

        if (value.isNullOrBlank()) {
            throw IllegalArgumentException("{$field}")
        }
    }

    // 最大長チェック
    fun checkSize(field: String, value: String, maxSize: Int) {

        if (!value.isNullOrEmpty() && value.length > maxSize) {
            throw IllegalArgumentException("")
        }
    }

    // 正規表現チェック
    fun checkMatchPattern(field: String, value: String, pattern: String) {

        val regex = Regex(pattern = pattern)
        if (!value.isNullOrEmpty() && !regex.matches(value)) {
            throw IllegalArgumentException("")
        }
    }
}