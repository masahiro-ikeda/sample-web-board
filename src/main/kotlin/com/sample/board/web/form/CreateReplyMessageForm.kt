package com.sample.board.web.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class CreateReplyMessageForm(
    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 100, message = "{error.validation.sizeOver}")
    var content: String?,

    @field:NotEmpty(message = "{error.validation.null}")
    @field:Pattern(
        regexp = "^[0-9]+\$",
        message = "{error.validation.illegalPattern}"
    )
    var postNo: String?
)