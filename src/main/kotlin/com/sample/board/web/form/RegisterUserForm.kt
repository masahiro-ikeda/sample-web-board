package com.sample.board.web.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterUserForm(
    //@field:NotEmpty(message = "nullだめ")
    //@field:Size(max = 25, message = "25文字以下")
    //@field:Pattern(regexp = "^[a-zA-Z0-9]+\$", message = "半角英数のみ")
    var userId: String?,

    //@field:NotEmpty(message = "nullだめ")
    //@field:Size(max = 25, message = "25文字以下")
    //@field:Pattern(regexp = "^[a-zA-Z0-9]+\$", message = "半角英数のみ")
    var password: String?,

    //@field:NotEmpty(message = "nullだめ")
    //@field:Size(max = 25, message = "25文字以下")
    var userName: String?
)