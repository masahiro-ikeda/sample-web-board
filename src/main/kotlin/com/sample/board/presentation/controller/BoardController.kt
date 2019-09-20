package com.sample.board.presentation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class BoardController(
) {
    @GetMapping
    fun displayBoard(): String {
        return "board"
    }
}