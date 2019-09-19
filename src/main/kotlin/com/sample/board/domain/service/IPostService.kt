package com.sample.board.domain.service

import com.sample.board.domain.model.Post
import com.sample.board.domain.model.PostAggregates
import com.sample.board.domain.service.dto.MessageCreateDto
import com.sample.board.domain.service.dto.UserCreateDto
import org.springframework.stereotype.Service

interface IPostService {
    fun createMessage(dto: MessageCreateDto)
    fun deleteMessage(id: String)
    fun fetchMessage(): List<Post>
}