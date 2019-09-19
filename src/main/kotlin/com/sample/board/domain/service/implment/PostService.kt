package com.sample.board.domain.service.implment

import com.sample.board.domain.model.Post
import com.sample.board.domain.model.PostAggregates
import com.sample.board.domain.model.enumeration.MessageType
import com.sample.board.domain.service.IPostService
import com.sample.board.domain.service.dto.MessageCreateDto
import com.sample.board.domain.service.dto.MessageDto
import com.sample.board.domain.service.repository.IPostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val repository: IPostRepository
) : IPostService {
    override fun createMessage(dto: MessageCreateDto) {

        var postNo = 0
        var replyNo = 0
        if (dto.messageType == MessageType.POST) {
            postNo = repository.fetchMaxPostNo()
            replyNo = 0
        } else {
            postNo = dto.postNo
            replyNo = repository.fetchMaxReplyNo(dto.postNo)
        }

        val message = MessageDto(
            dto.messageType,
            postNo,
            replyNo,
            dto.userId,
            dto.content,
            0
        )

        repository.create(message)
    }

    override fun deleteMessage(id: String) {

    }

    override fun fetchMessage(): List<Post> {

        val messages = PostAggregates(repository.fetchAll() ?: throw RuntimeException()).getPosts()
        return messages!!
    }
}