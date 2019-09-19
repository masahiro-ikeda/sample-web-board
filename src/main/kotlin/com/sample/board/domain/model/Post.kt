package com.sample.board.domain.model

import com.sample.board.domain.model.enumeration.MessageType
import java.util.stream.Collectors.toList

data class Post(
    val post: Message,
    val reply: List<Message>
)

class PostAggregates(private val messages: List<Message>) {
    var posts: ArrayList<Post> = ArrayList()

    init {
        messages.forEach {
            if (it.messageType == MessageType.POST) {
                val post: Message = it
                val reply: List<Message> = messages.stream().filter {
                    it.messageType == MessageType.REPLY && it.postNo == post.postNo
                }.collect(toList())
                posts.add(Post(post, reply))
            }
        }
    }

    fun getPosts(): List<Post>? {
        if (posts.size == 0) {
            return null
        }
        return posts
    }
}