package com.sample.board.domain.message

interface IMessageRepository {
    fun store(message: Message)
    fun remove(message: Message)
}