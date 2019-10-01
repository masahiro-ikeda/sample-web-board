package com.sample.board.application

import com.sample.board.application.dto.PostGoodDto
import com.sample.board.application.dto.RemoveGoodDto

interface IGoodService {
    fun postGood(dto: PostGoodDto)
    fun removeGood(dto: RemoveGoodDto)
}