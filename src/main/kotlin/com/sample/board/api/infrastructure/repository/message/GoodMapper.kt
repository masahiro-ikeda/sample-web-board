package com.sample.board.api.infrastructure.repository.message

import com.sample.board.api.domain.entity.message.Good
import com.sample.board.api.domain.repository.dto.GoodDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface GoodMapper {

    @Select(
        """
        SELECT
          id,
          message_id,
          user_id,
          is_deleted
        FROM
          goods
        WHERE
          message_id = #{id} AND
          is_deleted = 0
        """
    )
    fun selectByMessageId(messageId: Int): List<GoodDto>?

    @Select(
        """
        SELECT
          id,
          message_id,
          user_id,
          is_deleted
        FROM
          goods
        WHERE
          is_deleted = 0
        """
    )
    fun selectAll(): List<GoodDto>?

    @Insert(
        """
        INSERT INTO goods (
          id,
          message_id,
          user_id,
          is_deleted,
          created_at,
          updated_at
        ) VALUES (
          #{id},
          #{messageId},
          #{userId},
          #{isDeleted},
          NOW(),
          NOW()
        )
        """
    )
    fun insert(good: Good)

    @Update(
        """
        UPDATE 
          goods
        SET
          is_deleted = #{isDeleted},
          updated_at = NOW()
        WHERE
          id = #{id}
        """
    )
    fun update(good: Good)
}
