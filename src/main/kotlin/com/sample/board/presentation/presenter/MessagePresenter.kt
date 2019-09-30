package com.sample.board.presentation.presenter

import com.sample.board.domain.message.MessageForDisplay
import com.sample.board.domain.message.MessageType
import com.sample.board.domain.user.LoginUser
import org.springframework.stereotype.Component

@Component
class MessagePresenter {

    fun formatToHtml(messages: List<MessageForDisplay>, loginUser: LoginUser): String {
        val builder = StringBuilder()

        messages.stream().filter {
            it.messageType == MessageType.MESSAGE
        }.forEach { message ->
            // メッセージを描画
            builder.append(formatMessage(message))
            builder.append(formatGoodForm(message, loginUser))

            // 返信を描画
            messages.stream().filter {
                it.isReply() && it.postNo.equals(message.postNo)
            }.forEach { reply ->
                builder.append(formatReply(reply))
                builder.append(formatGoodForm(reply, loginUser))
            }

            // 返信欄を描画
            builder.append(formatReplyForm(message))
        }
        return builder.toString()
    }

    private fun formatMessage(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<p class=\"message\">投稿No: ${target.postNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${formatText(target.comment)}</p><br>")
        builder.append("<br>")
        return builder.toString()
    }

    private fun formatReply(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<p class=\"reply\">返信No: ${target.replyNo} / 投稿者: ${target.userName} / 投稿時間: ${target.createdAt}</p><br>")
        builder.append("<br>")
        builder.append("<p>${formatText(target.comment)}</p><br>")
        builder.append("<br>")
        return builder.toString()
    }

    private fun formatGoodForm(target: MessageForDisplay, loginUser: LoginUser): String {
        val builder = StringBuilder()
        builder.append("<form id=\"goodTo${target.id}\">")
        builder.append("<input type=\"hidden\" name=\"id\" value=\"${target.id}\">")
        builder.append("<input type=\"hidden\" name=\"goodStatus\" value=\"${if (target.isAlreadyGood(loginUser.username)) "ENABLE" else "DISABLE"}\">")
        builder.append("<input type=\"button\" value=\"${target.getNumberOfGood()} いいね\" onclick=\"postGood('goodTo${target.id}')\"/>")
        builder.append("</form>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReplyForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<form id=\"replyTo${target.postNo}\">")
        builder.append("<input type=\"hidden\" name=\"postNo\" value=\"${target.postNo}\">")
        builder.append("<textarea name=\"comment\" maxlength=\"100\" cols=\"40\" rows=\"2\"></textarea><br>")
        builder.append("<input type=\"button\" value=\"返信\" onclick=\"postReply('replyTo${target.postNo}')\"/><br>")
        builder.append("</form>")
        builder.append("<hr>")
        return builder.toString()
    }

    /**
     * 長文テキストデータをWebページ表示用に整形
     */
    private fun formatText(input: String): String {
        var output = input

        // サニタイズ
        output = output.replace("&", "&amp;")
        output = output.replace("\"", "&quot;")
        output = output.replace("<", "&lt;")
        output = output.replace(">", "&gt;")
        output = output.replace("'", "&#39;")

        // 改行コード変換
        output = output.replace(System.lineSeparator(), "<br>")

        return output
    }
}