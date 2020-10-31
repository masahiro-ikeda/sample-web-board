package com.sample.board.api.domain.entity.user

import com.sample.board.api.common.exception.BusinessError
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class User {
    val id: String
    var password: String
    var name: String
    var role: UserRole
    var isInvalid: Int

    /**
     * 新規登録時のコンストラクタ
     */
    constructor(id: String, password: String, name: String, role: String) {

        if (id.length > 20) {
            throw BusinessError("ユーザーIDは20文字まで登録できます。")
        }
        if (name.length > 50) {
            throw BusinessError("ユーザー名は50文字まで登録できます。")
        }
        if (password.length < 10) {
            throw BusinessError("パスワードは10文字以上登録してください。")
        }
        if (UserRole.valueOf(role) == null) {
            throw BusinessError("不正な権限が入力されています。")
        }

        this.id = id
        this.password = BCryptPasswordEncoder().encode(password)
        this.name = name
        this.role = UserRole.valueOf(role)
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
    fun editProfile(name: String){
        if (name.length > 50) {
            throw BusinessError("ユーザー名は50文字まで登録できます。")
        }
        this.name = name
    }

    /**
     * パスワードを再登録する.
     */
    fun renewPassword(password: String){
        if (password.length < 10) {
            throw BusinessError("パスワードは10文字以上登録してください。")
        }
        this.password = BCryptPasswordEncoder().encode(password)
    }

    /**
     * 退会させる.
     */
    fun withdraw() {
        this.isInvalid = 1
    }
}



