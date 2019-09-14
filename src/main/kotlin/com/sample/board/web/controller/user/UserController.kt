package com.sample.board.web.controller.user

import com.sample.board.service.UserService
import com.sample.board.service.dto.UserCreateDto
import com.sample.board.web.form.RegisterUserForm
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
class UserController(val service: UserService) {

    @GetMapping("register")
    fun showUserRegister(model: Model, @ModelAttribute("RegisterUserForm") input: RegisterUserForm): String {
        model.addAttribute("RegisterUserForm", input)
        return "user-register"
    }

    @PostMapping("register")
    fun registerUser(
        model: Model, @ModelAttribute("RegisterUserForm") @Valid input: RegisterUserForm,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {

        // バリデーションエラーが存在する場合
        if (bindingResult.hasErrors()) {
            // 入力値を戻して入力画面を再表示
            model.addAttribute("RegisterUserForm", input)
            return "user-register"
        }

        // サービスの登録処理の呼び出し
        val dto = UserCreateDto(
            input.userId ?: throw Exception(), // バリデーションチェックしてるのでnullはないはず
            input.password ?: throw Exception(),
            input.userName ?: throw Exception()
        )
        service.createUser(dto)

        return "redirect:../login"
    }
}