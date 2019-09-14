package com.sample.board.web.controller.user

import com.sample.board.domain.model.LoginUser
import com.sample.board.web.form.RegisterUserForm
import org.springframework.security.core.annotation.AuthenticationPrincipal

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
@RequestMapping("user")
class UserController {

    @GetMapping("register")
    fun showUserRegister(model: Model, @ModelAttribute("RegisterUserForm") input: RegisterUserForm): String {
        model.addAttribute("RegisterUserForm", input)
        return "user-register"
    }

    @PostMapping("register")
    fun registerUser(
        model: Model, @ModelAttribute("RegisterUserForm") @Valid input: RegisterUserForm,
        bindingResult: BindingResult,
        @AuthenticationPrincipal loginUser: LoginUser,
        redirectAttributes: RedirectAttributes
    ): String {

        // バリデーションエラーが存在する場合
        if (bindingResult.hasErrors()) {
            // 入力値を戻して入力画面を再表示
            model.addAttribute("RegisterUserForm", input)
            return "user-register"
        }

        return "redirect:login"
    }
}