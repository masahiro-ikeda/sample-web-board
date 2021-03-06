package com.sample.board.api.web.presenter

import com.sample.board.api.domain.entity.message.MessageType
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter


@Component
class MessagePresenter {

    companion object {
        val FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        const val SEPARATOR = " / "
    }

    fun formatToHtml(messages: List<MessageForDisplay>, loginUserId: String): String {
        val builder = StringBuilder()

        messages.stream().filter {
            it.messageType == MessageType.MESSAGE
        }.forEach { message ->
            // メッセージを描画
            builder.append(formatMessage(message, loginUserId))

            // 返信を描画
            messages.stream().filter {
                it.messageType == MessageType.REPLY
                && it.replyTo == message.messageId
            }.forEach { reply ->
                builder.append(formatReply(reply, loginUserId))
            }

            // 返信フォームを描画
            builder.append(formatReplyForm(message))
        }

        return builder.toString()
    }

    /**
     * 種別：MESSAGEをHTMLに描画する
     */
    private fun formatMessage(message: MessageForDisplay, loginUserId: String): String {
        val builder = StringBuilder()
        builder.append("<div class=\"message\">")
        // メッセージ本体
        builder.append(createMessageBody(message))

        builder.append("<table><tr><td>")
        // いいねボタン
        builder.append(createGoodForm(message, loginUserId))
        builder.append("</td>")
        // 削除ボタン
        if (message.userId == loginUserId) {
            builder.append("<td>")
            builder.append(createDeleteForm(message))
            builder.append("</td>")
        }
        builder.append("</table>")

        builder.append("</div>")
        return builder.toString()
    }

    /**
     * 種別：REPLYをHTMLに描画する
     */
    private fun formatReply(reply: MessageForDisplay, loginUserId: String): String {
        val builder = StringBuilder()
        builder.append("<div class=\"reply\">")
        // リプライ本体
        builder.append(createReplyBody(reply))

        builder.append("<table><tr><td>")
        // いいねボタン
        builder.append(createGoodForm(reply, loginUserId))
        builder.append("</td>")
        // 削除ボタン
        if (reply.userId == loginUserId) {
            builder.append("<td>")
            builder.append(createDeleteForm(reply))
            builder.append("</td>")
        }
        builder.append("</table>")

        builder.append("</div>")
        return builder.toString()
    }

    private fun createMessageBody(body: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<p>")
        builder.append("投稿No: ${body.messageId}")
        builder.append(SEPARATOR)
        builder.append("投稿者: ${body.userName}")
        builder.append(SEPARATOR)
        builder.append("投稿時間: ${body.updatedAt.format(FORMATTER)}")
        builder.append("</p>")
        builder.append("</br>")
        builder.append("<p>${formatText(body.comment)}</p>")
        builder.append("</br>")
        return builder.toString()
    }

    private fun createReplyBody(body: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<p>")
        builder.append("返信No: ${body.messageId}")
        builder.append(SEPARATOR)
        builder.append("投稿者: ${body.userName}")
        builder.append(SEPARATOR)
        builder.append("投稿時間: ${body.updatedAt.format(FORMATTER)}")
        builder.append("</p>")
        builder.append("</br>")
        builder.append("<p>${formatText(body.comment)}</p>")
        builder.append("</br>")
        return builder.toString()
    }

    private fun createGoodForm(target: MessageForDisplay, loginUserId: String): String {
        val builder = StringBuilder()
        builder.append("<form id=\"goodTo${target.messageId}\">")
        if (target.goods.filter { good -> good.getUserId().equals(loginUserId) }.isNotEmpty()) {
            // いいね済みの場合は削除フォームを生成
            builder.append("<input type=\"button\" value=\"${target.goods.size} いいね\" onclick=\"deleteGood('${target.messageId}')\"/>")
        } else {
            builder.append("<input type=\"button\" value=\"${target.goods.size} いいね\" onclick=\"postGood('${target.messageId}')\"/>")
        }
        builder.append("</form>")
        return builder.toString()
    }

    private fun createDeleteForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<form id=\"delete-${target.messageId}\">")
        builder.append("<input type=\"button\" value=\"削除\" onclick=\"deleteMessage('${target.messageId}')\"/>")
        builder.append("</form>")
        return builder.toString()
    }

    private fun formatReplyForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<div class=\"reply-form\">")
        builder.append("<form id=\"reply-${target.messageId}\">")
        builder.append("<input type=\"hidden\" name=\"messageId\" value=\"${target.messageId}\">")
        builder.append("<textarea name=\"comment\" maxlength=\"100\" cols=\"40\" rows=\"2\"></textarea><br>")
        builder.append("<input type=\"button\" value=\"返信\" onclick=\"postReply('reply-${target.messageId}')\"/>")
        builder.append("</form>")
        builder.append("</div>")
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
        output = output.replace("\n", "<br>")

        return output
    }
}