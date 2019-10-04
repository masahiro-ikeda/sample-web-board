package com.sample.board.application.exception

/**
 * 登録済みのユーザIDが使われた場合に発生させる例外
 */
class DuplicateUserException : Exception {
    constructor() : super("登録済みのユーザIDです。")
}