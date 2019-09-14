package com.sample.board.web.controller.error

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/error/{statusCode}")
class ErrorController {

    @GetMapping
    fun showErrorPage(@PathVariable statusCode: Int): String {
        /* ---------------------------------------------------- */
        /* 403エラーの処理 */
        /* ---------------------------------------------------- */
        if (statusCode == HttpServletResponse.SC_FORBIDDEN) {
            return "/error/403"
        }

        /* ---------------------------------------------------- */
        /* 404エラーの処理 */
        /* ---------------------------------------------------- */
        if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
            return "/error/404"
        }

        return "/error/500"
    }
}
