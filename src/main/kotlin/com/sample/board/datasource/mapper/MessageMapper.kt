package com.sample.board.datasource.mapper

import com.sample.board.domain.model.Message
import com.sample.board.domain.service.dto.MessageDto
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select


@Mapper
interface MessageMapper {

    @Insert(
        """
        INSERT INTO messages (
          message_type,
          post_no,
          reply_no,
          user_id,
          content,
          is_deleted,
          created_at,
          updated_at
        ) VALUES (
          #{messageType},
          #{postNo},
          #{replyNo},
          #{userId},
          #{content},
          #{isDeleted},
          NOW(),
          NOW()
        )
        """
    )
    fun insert(message: MessageDto)

    @Select(
        """
        SELECT
          id,
          message_type,
          post_no,
          reply_no,
          user_id,
          content,
          is_deleted,
          created_at,
          updated_at
        FROM
          messages
        ORDER BY
          post_no, reply_no
        """
    )
    fun select(): List<Message>

    @Delete(
        """
        DELETE FROM
          messages
        WHERE
          id = #{id}
        """
    )
    fun delete(id: Int)

    @Select(
        """
        SELECT
           IFNULL(max(post_no), 0)
        FROM
          messages
        """
    )
    fun selectMaxPostNo(): Int

    @Select(
        """
        SELECT
          max(reply_no)
        FROM
          messages
        WHERE
          post_no = #{postNo}
        """
    )
    fun selectMaxReplyNo(postNo: Int): Int
}