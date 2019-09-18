package com.sample.board.web.controller.user

import com.sample.board.domain.service.IUserService
import com.sample.board.domain.service.dto.UserCreateDto
import com.sample.board.domain.service.exception.CreateUserException
import com.sample.board.web.form.CreateUserForm
import com.sample.board.web.form.IFormatCheck
import com.sample.board.web.form.INullCheck
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
@RequestMapping("user")
class UserController(
    val service: IUserService,
    val validator: SmartValidator
) {

    companion object {
        const val CREATE_USER_FORM: String = "CreateUserForm"
    }

    @GetMapping("register")
    fun showUserRegister(
        model: Model, @ModelAttribute(CREATE_USER_FORM) input: CreateUserForm
    ): String {
        model.addAttribute(CREATE_USER_FORM, input)
        return "create-user"
    }

    @PostMapping("register")
    fun createUser(
        model: Model, @ModelAttribute(CREATE_USER_FORM) input: CreateUserForm, bindingResult: BindingResult
    ): String {

        // 必須チェック
        validator.validate(input, bindingResult, INullCheck::class.java)
        if (bindingResult.hasErrors()) {
            model.addAttribute(CREATE_USER_FORM, input)
            return "create-user"
        }

        // 形式チェック
        validator.validate(input, bindingResult, IFormatCheck::class.java)
        if (bindingResult.hasErrors()) {
            model.addAttribute(CREATE_USER_FORM, input)
            return "create-user"
        }

        try {
            val dto = UserCreateDto(
                input.userId!!,
                input.password!!,
                input.userName!!
            )
            service.createUser(dto)
        } catch (e: CreateUserException) {
            bindingResult.addError(FieldError(CREATE_USER_FORM, "userId", e.message!!))
            model.addAttribute(CREATE_USER_FORM, input)
            return "create-user"
        }

        return "redirect:../login"
    }
}
