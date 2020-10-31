package com.sample.board.api.common.exception

/**
 * 業務ルールに反する例外.
 */
class BusinessError : Exception {
    constructor(message: String) : super(message)
}