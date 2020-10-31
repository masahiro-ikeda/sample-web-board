package com.sample.board.api.web.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class RegisterUserForm(
    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 25, message = "{error.validation.sizeOver}")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+\$",
        message = "{error.validation.illegalPattern}"
    )
    var userId: String?,

    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 25, message = "{error.validation.sizeOver}")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+\$",
        message = "{error.validation.illegalPattern}"
    )
    var password: String?,

    @field:NotEmpty(message = "{error.validation.null}")
    @field:Size(max = 25, message = "{error.validation.sizeOver}")
    var userName: String?
)