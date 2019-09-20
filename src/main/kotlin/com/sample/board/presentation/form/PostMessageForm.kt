package com.sample.board.presentation.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class PostMessageForm(
    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 100, message = "{error.validation.sizeOver}")
    var content: String?
)