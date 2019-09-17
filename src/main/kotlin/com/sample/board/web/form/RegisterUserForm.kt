package com.sample.board.web.form

data class Error(
    var item: String?,
    var message: String
)

data class RegisterUserForm(
    var userId: String?,
    var password: String?,
    var userName: String?,
    var errors: List<Error>?
)