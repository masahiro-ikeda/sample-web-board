package com.sample.board.web.controller.board

import com.sample.board.domain.model.enumeration.MessageType
import com.sample.board.domain.service.IPostService
import com.sample.board.domain.service.dto.MessageCreateDto
import com.sample.board.domain.service.dto.UserCreateDto
import com.sample.board.domain.service.exception.CreateUserException
import com.sample.board.web.controller.user.UserController
import com.sample.board.web.form.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("board")
class BoardController(
    val service: IPostService,
    val validator: SmartValidator
) {

    companion object {
        const val CREATE_MESSAGE_FORM: String = "CreateMessageForm"
    }

    @GetMapping
    fun showBoard(model: Model): String {

        return "board"
    }
}