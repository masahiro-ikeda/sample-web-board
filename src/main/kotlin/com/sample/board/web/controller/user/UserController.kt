package com.sample.board.web.controller.user

import com.google.gson.Gson
import com.sample.board.service.UserService
import com.sample.board.service.dto.UserCreateDto
import com.sample.board.service.Result
import com.sample.board.web.form.RegisterUserForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
@RequestMapping("user")
class UserController(val service: UserService) {

    companion object {
        const val FORM_NAME: String = "RegisterUserForm"
    }

    @GetMapping("register")
    fun showUserRegister(model: Model, @ModelAttribute(FORM_NAME) input: RegisterUserForm): String {
        model.addAttribute(FORM_NAME, input)
        return "user-register"
    }

    @PostMapping("register")
    fun registerUser(
        model: Model, @ModelAttribute(FORM_NAME) input: RegisterUserForm,
        redirectAttributes: RedirectAttributes
    ): String {

        // サービスの登録処理の呼び出し
        val dto = UserCreateDto(
            input.userId,
            input.password,
            input.userName
        )

        val result: Result = service.createUser(dto)

        if (!result.isSuccess()) {
            return Gson().toJson(result)
        }

        return "redirect:../login"
    }
}