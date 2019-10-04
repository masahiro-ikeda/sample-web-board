package com.sample.board.presentation.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

interface IReplyCheck

class PostMessageForm(

    @field:NotEmpty(message = "{error.validation.null}")
    @field:Pattern(
        regexp = "[MESSAGE|REPLY]*",
        message = "{error.validation.illegalPattern}"
    )
    var type: String?,

    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 100, message = "{error.validation.sizeOver}")
    var comment: String?,

    @field:NotEmpty(message = "{error.validation.null}", groups = [IReplyCheck::class])
    @field:Pattern(
        regexp = "^[0-9]*\$",
        message = "{error.validation.illegalPattern}",
        groups = [IReplyCheck::class]
    )
    var postNo: String?
)