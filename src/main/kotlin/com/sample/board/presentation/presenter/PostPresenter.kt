package com.sample.board.presentation.presenter

import com.sample.board.domain.message.MessageForDisplay
import com.sample.board.domain.message.MessageType
import org.springframework.stereotype.Component

@Component
class PostPresenter() {

    fun formatToHtml(messages: List<MessageForDisplay>): String {
        val builder = StringBuilder()

        messages.stream().filter {
            it.body.messageType == MessageType.MESSAGE
        }.forEach { message ->
            // メッセージを描画
            builder.append(formatMessage(message))

            // 返信を描画
            messages.stream().filter {
                it.body.isReply() && it.body.postNo.equals(message.body.postNo)
            }.forEach { reply ->
                builder.append(formatReply(reply))
            }

            // 返信欄を描画
            builder.append(formatReplyForm(message))
        }
        return builder.toString()
    }

    private fun formatMessage(target: MessageForDisplay): String {
        val builder = StringBuilder()
        val message = target.body
        builder.append("<p class=\"message\">投稿No: ${message.postNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${message.comment}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReply(target: MessageForDisplay): String {
        val builder = StringBuilder()
        val reply = target.body
        builder.append("<p class=\"reply\">返信No: ${reply.replyNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${reply.comment}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReplyForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        val message = target.body

        builder.append("<form id=\"replyTo${message.postNo}\">")
        builder.append("<input type=\"hidden\" name=\"postNo\" value=\"${message.postNo}\">")
        builder.append("<textarea name=\"comment\" maxlength=\"100\" cols=\"40\" rows=\"2\"></textarea><br>")
        builder.append("<input type=\"button\" value=\"返信\" onclick=\"postReply('replyTo${message.postNo}')\"/><br>")
        builder.append("</form>")
        return builder.toString()
    }
}