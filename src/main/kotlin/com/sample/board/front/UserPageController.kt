package com.sample.board.front

import com.sample.board.api.application.UserService
import com.sample.board.api.common.exception.BusinessError
import com.sample.board.api.web.form.RegisterUserForm
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
class UserPageController(
    val service: UserService,
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

        // バリデーションチェック
        validator.validate(input, bindingResult)
        if (bindingResult.hasErrors()) {
            model.addAttribute(REGISTER_USER_FORM, input)
            return "register-user"
        }

        try {
            service.create(
                input.userId!!, input.password!!,
                input.userName!!
            )

        } catch (e: BusinessError) {
            // 業務エラー発生時は登録画面に通知
            bindingResult.addError(
                FieldError(
                    bindingResult.objectName, "userId", input.userId!!, false, null, null, e.message!!
                )
            )
            model.addAttribute(REGISTER_USER_FORM, input)
            return "register-user"
        }

        return "redirect:../login"
    }
}
