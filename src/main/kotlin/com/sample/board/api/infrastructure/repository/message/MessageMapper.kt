package com.sample.board.api.infrastructure.repository.message

import com.sample.board.api.domain.entity.message.Message
import com.sample.board.api.domain.repository.dto.MessageDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface MessageMapper {

    @Select(
        """
        SELECT
          id,
          type,
          reply_to,
          user_id,
          comment,
          is_deleted,
          created_at,
          updated_at
        FROM
          messages
        WHERE
          id = #{id}
        """
    )
    fun selectById(id: Int): MessageDto?

    @Select(
        """
        SELECT
          id,
          type,
          reply_to,
          user_id,
          comment,
          is_deleted,
          created_at,
          updated_at
        FROM
          messages
        ORDER BY
          id
        """
    )
    fun selectAll(): List<MessageDto>?

    @Insert(
        """
        INSERT INTO messages (
          id,
          type,
          reply_to,
          user_id,
          comment,
          is_deleted,
          created_at,
          updated_at
        ) VALUES (
          #{id},
          #{messageType},
          #{replyTo},
          #{userId},
          #{comment},
          #{isDeleted},
          NOW(),
          NOW()
        )
        """
    )
    fun insert(message: Message)

    @Update(
        """
        UPDATE messages SET
          type       = #{messageType},
          reply_to   = #{replyTo},
          user_id    = #{userId},
          comment    = #{comment},
          is_deleted = #{isDeleted},
          updated_at = NOW()
        WHERE
          id = #{id}
        """
    )
    fun update(message: Message)
}
