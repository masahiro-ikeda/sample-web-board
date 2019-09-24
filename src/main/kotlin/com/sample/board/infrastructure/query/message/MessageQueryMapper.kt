package com.sample.board.infrastructure.domain.message

import com.sample.board.query.dto.MessageDto
import com.sample.board.domain.message.Message
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MessageQueryMapper {

    @Select(
        """
        SELECT
          id,
          message_type,
          post_no,
          reply_no,
          user_id,
          comment,
          is_deleted
        FROM
          messages
        WHERE
          id = #{id} AND
          is_deleted = 0
        """
    )
    fun selectById(id: String): Message?

    @Select(
        """
        SELECT
          msg.id,
          msg.type as message_type,
          msg.post_no,
          msg.reply_no,
          msg.user_id,
          usr.name as user_name,
          msg.comment,
          msg.is_deleted,
          msg.created_at,
          msg.updated_at
        FROM
          messages msg
        INNER JOIN users usr
          ON msg.user_id = usr.id
        ORDER BY
          msg.post_no, msg.reply_no
        """
    )
    fun selectAll(): List<MessageDto>?

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
