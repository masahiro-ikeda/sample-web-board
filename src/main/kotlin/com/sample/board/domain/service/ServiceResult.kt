package com.sample.board.domain.service

data class Error(
    val item: String?,
    val exception: Exception
)

class ServiceResult {
    private var errors = mutableListOf<Error>()

    fun addError(item: String?, e: Exception) {
        errors.add(Error(item, e))
    }

    fun isSuccess(): Boolean {
        return (errors.size == 0)
    }

    fun getErrors(): List<Error> {
        return errors
    }
}