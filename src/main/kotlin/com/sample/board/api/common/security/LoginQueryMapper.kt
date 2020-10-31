package com.sample.board.api.common.security

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface LoginQueryMapper {

    @Select(
        """
        SELECT
          id,
          password,
          name,
          role
        FROM
          users
        WHERE
          id = #{id}
          AND is_invalid = 0
        """
    )
    fun selectById(id: String): LoginUserDto?
}