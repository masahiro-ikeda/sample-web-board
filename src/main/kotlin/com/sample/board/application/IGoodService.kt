package com.sample.board.application

import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.CancelGoodDto

interface IGoodService {
    fun postGood(dto: PostGoodDto)
    fun cancelGood(dto: CancelGoodDto)
}