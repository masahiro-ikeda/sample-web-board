package com.sample.board.api.common.security

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class LoginPageController {

    @RequestMapping(value = ["login"], method = [RequestMethod.GET])
    fun showLoginPage(model: Model): String {
        return "login"
    }
}
