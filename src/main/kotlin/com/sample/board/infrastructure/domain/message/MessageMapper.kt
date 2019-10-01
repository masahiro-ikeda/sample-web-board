package com.sample.board.infrastructure.domain.message

import com.sample.board.domain.message.Message
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface MessageMapper {

    @Insert(
        """
        INSERT INTO messages (
          id,
          type,
          post_no,
          reply_no,
          user_id,
          comment,
          is_deleted,
          created_at,
          updated_at
        ) VALUES (
          #{id},
          #{messageType},
          #{postNo},
          #{replyNo},
          #{userId},
          #{comment},
          #{isDeleted},
          NOW(),
          NOW()
        )
        """
    )
    fun insert(message: Message)

    @Select(
        """
        SELECT
          id
        FROM
          messages
        WHERE
          id = #{id} AND
          is_deleted = 0
        """
    )
    fun selectById(id: String): String

    @Update(
        """
        UPDATE 
          messages
        SET
          is_deleted = 1,
          updated_at = NOW()
        WHERE
          id = #{id}
        """
    )
    fun delete(id: String)
}
