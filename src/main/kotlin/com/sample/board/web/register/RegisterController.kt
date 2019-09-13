package com.sample.board.web.register

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("register")
class RegisterController {

    @GetMapping
    fun showLogin(model: Model): String {
        return "register"
    }
}