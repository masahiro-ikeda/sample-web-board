package com.sample.board.infrastructure.domain.user

import com.sample.board.domain.user.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {

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
}