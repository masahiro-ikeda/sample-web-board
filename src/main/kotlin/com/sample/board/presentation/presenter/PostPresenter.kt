package com.sample.board.presentation.presenter

import com.sample.board.domain.message.MessageForDisplay
import com.sample.board.query.dto.MessageDto
import org.springframework.stereotype.Component

@Component
class PostPresenter() {

    fun formatToHtml(messages: List<MessageForDisplay>): String {
        val builder = StringBuilder()

        messages.forEach {
            val message = it.message
            if (!message.isReply()){
                builder.append(formatMessage(it))
            } else {
                builder.append(formatReply(it))
            }
        }

        return builder.toString()
    }

    private fun formatMessage(target: MessageForDisplay): String {
        val builder = StringBuilder()
        val message = target.message
        builder.append("<p class=\"message\">投稿No: ${message.postNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${message.comment}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReply(target: MessageForDisplay): String {
        val builder = StringBuilder()
        val message = target.message
        builder.append("<p class=\"reply\">返信No: ${message.replyNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${message.comment}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }
}