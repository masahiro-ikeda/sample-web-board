package com.sample.board.web.controller.board

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("board")
class BoardController {

    @GetMapping
    fun showBoard(model: Model): String {
        return "board"
    }
}