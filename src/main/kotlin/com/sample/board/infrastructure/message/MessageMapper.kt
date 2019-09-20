package com.sample.board.infrastructure.message

import com.sample.board.application.dto.DisplayMessageDto
import com.sample.board.domain.message.Message
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MessageMapper {

    @Insert(
        """
        INSERT INTO messages (
          id,
          message_type,
          post_no,
          reply_no,
          user_id,
          content,
          is_deleted,
          created_at,
          updated_at
        ) VALUES (
          #{id},
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
    fun insert(message: Message)

    @Select(
        """
        SELECT
          msg.id,
          msg.message_type,
          msg.post_no,
          msg.reply_no,
          msg.user_id,
          usr.name as user_name,
          msg.content,
          msg.is_deleted,
          msg.created_at as post_time
        FROM
          messages msg
        INNER JOIN users usr
          ON msg.user_id = usr.id
        ORDER BY
          msg.post_no, msg.reply_no
        """
    )
    fun select(): List<DisplayMessageDto>

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
