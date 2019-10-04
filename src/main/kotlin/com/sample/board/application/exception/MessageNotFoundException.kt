package com.sample.board.application.exception

/**
 * 存在しない投稿メッセージが指定された場合に発生させる例外
 */
class MessageNotFoundException : RuntimeException {
    constructor(
        field: String,
        value: String
    ) : super(
        "${field}: ${value} 対象の投稿メッセージが見つかりません。"
    )
}