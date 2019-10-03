package com.sample.board.presentation.presenter

import com.sample.board.domain.message.MessageForDisplay
import com.sample.board.domain.message.MessageType
import com.sample.board.domain.user.LoginUser
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter


@Component
class MessagePresenter {

    companion object {
        val FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        const val SEPARATOR = " / "
    }

    fun formatToHtml(messages: List<MessageForDisplay>, loginUser: LoginUser): String {
        val builder = StringBuilder()

        messages.stream().filter {
            it.messageType == MessageType.MESSAGE
        }.forEach { message ->
            // メッセージを描画
            builder.append(formatMessage(message, loginUser))

            // 返信を描画
            messages.stream().filter {
                it.isReply() && it.postNo.equals(message.postNo)
            }.forEach { reply ->
                builder.append(formatReply(reply, loginUser))
            }

            // 返信フォームを描画
            builder.append(formatReplyForm(message))
        }

        return builder.toString()
    }

    /**
     * 種別：MESSAGEをHTMLに描画する
     */
    private fun formatMessage(message: MessageForDisplay, loginUser: LoginUser): String {
        val builder = StringBuilder()
        builder.append("<div class=\"message\">")
        // メッセージ本体
        builder.append(createMessageBody(message))

        builder.append("<table><tr><td>")
        // いいねボタン
        builder.append(createGoodForm(message, loginUser))
        builder.append("</td>")
        // 削除ボタン
        if (message.userId == loginUser.username) {
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
    private fun formatReply(reply: MessageForDisplay, loginUser: LoginUser): String {
        val builder = StringBuilder()
        builder.append("<div class=\"reply\">")
        // リプライ本体
        builder.append(createReplyBody(reply))

        builder.append("<table><tr><td>")
        // いいねボタン
        builder.append(createGoodForm(reply, loginUser))
        builder.append("</td>")
        // 削除ボタン
        if (reply.userId == loginUser.username) {
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
        builder.append("投稿No: ${body.postNo}")
        builder.append(SEPARATOR)
        builder.append("投稿者: ${body.userName}")
        builder.append(SEPARATOR)
        builder.append("投稿時間: ${body.createdAt.format(FORMATTER)}")
        builder.append("</p>")
        builder.append("</br>")
        builder.append("<p>${formatText(body.comment)}</p>")
        builder.append("</br>")
        return builder.toString()
    }

    private fun createReplyBody(body: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<p>")
        builder.append("返信No: ${body.replyNo}")
        builder.append(SEPARATOR)
        builder.append("投稿者: ${body.userName}")
        builder.append(SEPARATOR)
        builder.append("投稿時間: ${body.createdAt.format(FORMATTER)}")
        builder.append("</p>")
        builder.append("</br>")
        builder.append("<p>${formatText(body.comment)}</p>")
        builder.append("</br>")
        return builder.toString()
    }

    private fun createGoodForm(target: MessageForDisplay, loginUser: LoginUser): String {
        val builder = StringBuilder()
        builder.append("<form id=\"goodTo${target.id}\">")
        if (target.isAlreadyGood(loginUser.username)) {
            // いいね済みの場合は削除フォームを生成
            builder.append("<input type=\"button\" value=\"${target.getNumberOfGood()} いいね\" onclick=\"deleteGood('${target.id}')\"/>")
        } else {
            builder.append("<input type=\"button\" value=\"${target.getNumberOfGood()} いいね\" onclick=\"postGood('${target.id}')\"/>")
        }
        builder.append("</form>")
        return builder.toString()
    }

    private fun createDeleteForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<form id=\"delete-${target.id}\">")
        builder.append("<input type=\"button\" value=\"削除\" onclick=\"deleteMessage('${target.id}')\"/>")
        builder.append("</form>")
        return builder.toString()
    }

    private fun formatReplyForm(target: MessageForDisplay): String {
        val builder = StringBuilder()
        builder.append("<div class=\"reply-form\">")
        builder.append("<form id=\"reply-${target.postNo}\">")
        builder.append("<input type=\"hidden\" name=\"postNo\" value=\"${target.postNo}\">")
        builder.append("<textarea name=\"comment\" maxlength=\"100\" cols=\"40\" rows=\"2\"></textarea><br>")
        builder.append("<input type=\"button\" value=\"返信\" onclick=\"postReply('reply-${target.postNo}')\"/>")
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