package com.sample.board.api.domain.entity.user

import com.sample.board.api.common.exception.BusinessError
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class User {
    /* ユーザーID */
    private val id: String
    /* パスワード */
    private var password: String
    /* ユーザー名 */
    private var name: String
    /* 権限 */
    private var role: UserRole
    /* 有効フラグ */
    private var isInvalid: Int

    // 定数
    companion object {
        /* ユーザーIDの最大字数 */
        const val MAX_LENGTH_USER_ID: Int = 20
        /* ユーザー名の最大字数 */
        const val MAX_LENGTH_USER_NAME: Int = 50
        /* パスワードの最小字数 */
        const val MIN_LENGTH_PASSWORD: Int = 10
    }

    /**
     * 新規登録時のコンストラクタ
     */
    constructor(id: String, password: String, name: String, role: UserRole) {

        if (id.length > MAX_LENGTH_USER_ID) {
            throw BusinessError("ユーザーIDは${MAX_LENGTH_USER_ID}文字まで登録できます。")
        }
        if (name.length > MAX_LENGTH_USER_NAME) {
            throw BusinessError("ユーザー名は${MAX_LENGTH_USER_NAME}文字まで登録できます。")
        }
        if (password.length < MIN_LENGTH_PASSWORD) {
            throw BusinessError("パスワードは${MIN_LENGTH_PASSWORD}文字以上登録してください。")
        }

        this.id = id
        this.password = BCryptPasswordEncoder().encode(password)
        this.name = name
        this.role = role
        this.isInvalid = 0
    }

    /**
     * データ読み込み時に使用.
     */
    constructor(id: String, password: String, name: String, role: String, isInvalid: Int) {
        this.id = id
        this.password = password
        this.name = name
        this.role = UserRole.valueOf(role)
        this.isInvalid = isInvalid
    }

    /**
     * プロフィールを編集する.
     * 名前のみ編集可
     */
    fun editProfile(name: String) {
        if (name.length > MAX_LENGTH_USER_NAME) {
            throw BusinessError("ユーザー名は${MAX_LENGTH_USER_NAME}文字まで登録できます。")
        }
        this.name = name
    }

    /**
     * パスワードを再登録する.
     */
    fun renewPassword(password: String) {
        if (password.length < MIN_LENGTH_PASSWORD) {
            throw BusinessError("パスワードは${MIN_LENGTH_PASSWORD}文字以上登録してください。")
        }
        this.password = BCryptPasswordEncoder().encode(password)
    }

    /**
     * 退会させる.
     */
    fun withdraw() {
        this.isInvalid = 1
    }

    /**
     * 有効なユーザーか？
     */
    fun isInvalid(): Boolean{
        return this.isInvalid == 1
    }

    // 以下、Getter
    fun getId(): String {
        return this.id
    }

    fun getPassword(): String {
        return this.password
    }

    fun getName(): String {
        return this.name
    }

    fun getRole(): UserRole {
        return this.role
    }
}
