package com.sample.board.web.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class CreateUserForm(
    @field:NotEmpty(message = "{error.validation.null}", groups = [INullCheck::class])
    @field:Size(max = 25, message = "{error.validation.sizeOver}", groups = [IFormatCheck::class])
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+\$",
        message = "{error.validation.illegalPattern}",
        groups = [IFormatCheck::class]
    )
    var userId: String?,

    @field:NotEmpty(message = "{error.validation.null}", groups = [INullCheck::class])
    @field:Size(max = 25, message = "{error.validation.sizeOver}", groups = [IFormatCheck::class])
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+\$",
        message = "{error.validation.illegalPattern}",
        groups = [IFormatCheck::class]
    )
    var password: String?,

    @field:NotEmpty(message = "{error.validation.null}", groups = [INullCheck::class])
    @field:Size(max = 25, message = "{error.validation.sizeOver}", groups = [IFormatCheck::class])
    var userName: String?
)