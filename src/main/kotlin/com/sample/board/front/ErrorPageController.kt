package com.sample.board.front

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.util.NestedServletException

/**
 * アプリケーション共通エラー制御.
 */
@Controller
class ErrorPageController : ErrorController {

    // ----------------------------------------------------------------------
    // インスタンスメソッド
    // ----------------------------------------------------------------------
    /**
     * 初期読込処理.
     */
    @RequestMapping(value = ["error"])
    fun index(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {

        /* ---------------------------------------------------- */
        /* 400エラーの処理 */
        /* ---------------------------------------------------- */
        if (response.status == HttpServletResponse.SC_BAD_REQUEST) {
            return "redirect:/error/400"
        }

        /* ---------------------------------------------------- */
        /* 403エラーの処理 */
        /* ---------------------------------------------------- */
        if (response.status == HttpServletResponse.SC_FORBIDDEN) {
            return "redirect:/error/403"
        }

        /* ---------------------------------------------------- */
        /* 404エラーの処理 */
        /* ---------------------------------------------------- */
        if (response.status == HttpServletResponse.SC_NOT_FOUND) {
            return "redirect:/error/404"
        }

        // システム例外例外
        var throwable: Throwable? = request.getAttribute("javax.servlet.error.exception") as Throwable
        /* ---------------------------------------------------- */
        /* 例外処理 */
        /* ---------------------------------------------------- */
        if (throwable != null) {

            // NestedServletExceptionでラップされている場合の対応
            if (throwable is NestedServletException && throwable.cause != null) {
                throwable = throwable.cause
            }
        }

        return "redirect:/error/500"
    }

    override fun getErrorPath(): String {
        return "error"
    }

    /**
     * システムエラー画面.
     */
    @RequestMapping(value = ["error/500"])
    fun status500(request: HttpServletRequest, response: HttpServletResponse): String {
        response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        return "error/500"
    }

    /**
     * 400エラー画面.
     */
    @RequestMapping(value = ["error/400"])
    fun status400(request: HttpServletRequest, response: HttpServletResponse): String {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        return "error/400"
    }

    /**
     * 403エラー画面.
     */
    @RequestMapping(value = ["error/403"])
    fun status403(request: HttpServletRequest, response: HttpServletResponse): String {
        response.status = HttpServletResponse.SC_FORBIDDEN
        // 403エラーは404画面と同じ
        return "error/403"
    }

    /**
     * 404エラー画面.
     */
    @RequestMapping(value = ["error/404"])
    fun status404(request: HttpServletRequest, response: HttpServletResponse): String {
        response.status = HttpServletResponse.SC_NOT_FOUND
        return "error/404"
    }
}
