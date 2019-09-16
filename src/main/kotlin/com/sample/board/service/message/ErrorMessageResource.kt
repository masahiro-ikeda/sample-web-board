package com.sample.board.service.message

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import java.text.MessageFormat

@Configuration
@PropertySource(
    "classpath:/config/ErrorMessage.properties"
)
class ErrorMessageResource(val env: Environment) {

    companion object {
        const val NO_MESSAGE = "No Message."
    }

    /**
     * プロパティ値を取得します。
     * @param key プロパティキー
     * @return プロパティ値
     */
    fun get(key: String): String {
        return env.getProperty(key) ?: NO_MESSAGE
    }

    /**
     * 引数付きのプロパティ値を取得します。
     *
     * @param key プロパティキー
     * @param args 引数
     * @return プロパティ値
     */
    fun get(key: String, vararg args: Any): String {
        return MessageFormat.format(env.getProperty(key), *args) ?: NO_MESSAGE
    }
}
