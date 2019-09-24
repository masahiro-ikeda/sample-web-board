package com.sample.board.infrastructure.domain.message

import com.sample.board.domain.message.Good
import com.sample.board.domain.message.Message
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface GoodMapper {

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
          id = #{id} AND
          is_deleted = 0
        """
    )
    fun selectByMessageId(messageId: String): List<Good>

    @Update(
        """
        UPDATE 
          goods
        SET
          is_delete = 1,
          updatedAt = NOW()
        WHERE
          id = #{id}
        """
    )
    fun delete(id: String)
}
