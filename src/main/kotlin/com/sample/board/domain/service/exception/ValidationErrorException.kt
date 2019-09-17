package com.sample.board.domain.service.exception

/**
 * バリデーション違反に関する例外
 */
class ValidationErrorException(message: String) : Exception(message)