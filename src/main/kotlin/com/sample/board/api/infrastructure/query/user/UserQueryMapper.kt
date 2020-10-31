package com.sample.board.api.infrastructure.query.user

import com.sample.board.api.domain.entity.user.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserQueryMapper {

    @Select(
        """
        SELECT
          id,
          password,
          name,
          role,
          is_invalid,
          created_at,
          updated_at
        FROM
          users
        WHERE
          id = #{id}
        """
    )
    fun selectById(id: String): User?
}