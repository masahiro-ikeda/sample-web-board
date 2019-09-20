package com.sample.board.presentation.presenter

import com.sample.board.application.dto.DisplayMessageDto

class PostPresenter() {

    fun formatToHtml(): String {
        val builder = StringBuilder()

        return builder.toString()
    }

    private fun formatPost(target: DisplayMessageDto): String {
        val builder = StringBuilder()
        builder.append("<p class=\"post\">投稿No: ${target.postNo} / 投稿者: ${target.userId} / 投稿時間: ${target.postTime}</p><br>")
        builder.append("<br>")
        builder.append("<p>${target.content}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }

    private fun formatReply(target: DisplayMessageDto): String {
        val builder = StringBuilder()
        builder.append("<p class=\"reply\">返信No: ${target.postNo} / 投稿者: ${target.userId} / 投稿時間: ${target.postTime}</p><br>")
        builder.append("<br>")
        builder.append("<p>${target.content}</p><br>")
        builder.append("<br>")
        builder.append("<hr>")
        return builder.toString()
    }
}