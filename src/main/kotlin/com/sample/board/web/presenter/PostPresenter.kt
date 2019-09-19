package com.sample.board.web.presenter

import com.sample.board.domain.model.Message
import com.sample.board.domain.model.PostAggregates

class PostPresenter(private val target: PostAggregates) {

    fun formatToHtml(): String {
        val builder = StringBuilder()

        return builder.toString()
    }

    private fun formatPost(target: Message): String {
        val builder = StringBuilder()
        builder.append("<p class=\"post\">投稿No: ${target.postNo} / 投稿者: ${target.userId} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${target.content}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReply(target: Message): String {
        val builder = StringBuilder()
        builder.append("<p class=\"reply\">返信No: ${target.postNo} / 投稿者: ${target.userId} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${target.content}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }
}