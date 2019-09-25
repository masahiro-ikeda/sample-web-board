package com.sample.board.presentation.controller

import com.sample.board.application.IUserService
import com.sample.board.application.dto.RegisterUserDto
import com.sample.board.presentation.form.IFormatCheck
import com.sample.board.presentation.form.INullCheck
import com.sample.board.presentation.form.RegisterUserForm
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
        const val REGISTER_USER_FORM: String = "RegisterUserForm"
    }

    @GetMapping("register")
    fun displayUserRegisterPage(
        model: Model, @ModelAttribute(REGISTER_USER_FORM) input: RegisterUserForm
    ): String {
        model.addAttribute(REGISTER_USER_FORM, input)
        return "register-user"
    }

    @PostMapping("register")
    fun registerUser(
        model: Model, @ModelAttribute(REGISTER_USER_FORM) input: RegisterUserForm, bindingResult: BindingResult
    ): String {

        // 必須チェック
        validator.validate(input, bindingResult, INullCheck::class.java)
        if (bindingResult.hasErrors()) {
            model.addAttribute(REGISTER_USER_FORM, input)
            return "register-user"
        }

        // 形式チェック
        validator.validate(input, bindingResult, IFormatCheck::class.java)
        if (bindingResult.hasErrors()) {
            model.addAttribute(REGISTER_USER_FORM, input)
            return "register-user"
        }

        try {
            val dto = RegisterUserDto(
                input.userId!!,
                input.password!!,
                input.userName!!,
                input.userRole!!
            )
            service.registerUser(dto)
        } catch (e: IllegalArgumentException) {
            bindingResult.addError(FieldError(REGISTER_USER_FORM, "userId", e.message!!))
            model.addAttribute(REGISTER_USER_FORM, input)
            return "register-user"
        }

        return "redirect:../login"
    }
}
