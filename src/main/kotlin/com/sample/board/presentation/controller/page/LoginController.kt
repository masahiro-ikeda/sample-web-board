package com.sample.board.presentation.controller.page

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class LoginController {

    @RequestMapping(value = ["login"] , method = [RequestMethod.GET])
    fun displayLoginPage(model: Model): String {
        return "login"
    }

    /**
     * 掲示板画面を表示させる
     */
    @RequestMapping(value = ["/", "home"] , method = [RequestMethod.GET])
    fun displayHomePage(): String {
        return "board"
    }
}
