package com.sample.board.api.infrastructure.repository.user

import com.sample.board.api.domain.entity.user.User
import com.sample.board.api.domain.repository.dto.UserDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface UserMapper {

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
    fun selectById(id: String): UserDto?

    @Insert(
        """
        INSERT INTO users (
          id,
          password,
          name,
          role,
          is_invalid,
          created_at,
          updated_at
        ) VALUES (
          #{id},
          #{password},
          #{name},
          #{role},
          #{isInvalid},
          NOW(),
          NOW()
        )
        """
    )
    fun insert(user: User)

    @Update(
        """
        UPDATE users SET
          password =   #{password},
          name =       #{name},
          role =       #{role},
          is_invalid = #{isInvalid},
          updated_at = NOW()
        WHERE
          id = #{id}
        """
    )
    fun update(user: User)
}