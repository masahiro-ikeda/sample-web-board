package com.sample.board.service

enum class ResultType() {
    SUCCESS, FAILURE
}

data class InputError(
    val item: String,
    val reason: String
)

class Result {
    private var result = ResultType.SUCCESS
    private var error = mutableListOf<InputError>()

    fun setFailure(){
        this.result = ResultType.FAILURE
    }

    fun addError(item: String, message: String){
        error.add(InputError(item, message))
    }

    fun isSuccess(): Boolean{
        return result.equals(ResultType.SUCCESS)
    }
}