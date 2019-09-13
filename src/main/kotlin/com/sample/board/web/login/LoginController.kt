package com.sample.board.web.login

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("login")
class LoginController {

    @GetMapping
    fun showLogin(model: Model): String {
        return "login"
    }
}
