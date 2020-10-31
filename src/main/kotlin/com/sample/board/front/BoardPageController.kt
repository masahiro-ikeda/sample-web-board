package com.sample.board.front

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class BoardPageController {

    /**
     * 掲示板画面を表示させる.
     */
    @RequestMapping(value = ["/", "home"], method = [RequestMethod.GET])
    fun displayHomePage(): String {
        return "board"
    }
}