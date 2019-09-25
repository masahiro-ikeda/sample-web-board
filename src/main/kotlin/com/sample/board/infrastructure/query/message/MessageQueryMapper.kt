package com.sample.board.infrastructure.query.message

import com.sample.board.query.dto.MessageDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MessageQueryMapper {

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
        WHERE
          msg.id = #{id} AND
          msg.is_deleted = 0
        """
    )
    fun selectById(id: String): MessageDto?

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
        WHERE
          msg.is_deleted = 0
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
