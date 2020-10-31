package com.sample.board.api.infrastructure.query.message

import com.sample.board.api.domain.query.dto.MessageDisplayDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MessageQueryMapper {

    @Select(
        """
        SELECT
          msg.id,
          msg.type,
          msg.reply_to,
          msg.user_id,
          usr.name as user_name,
          msg.comment,
          msg.is_deleted,
          msg.updated_at
        FROM
          messages msg
        INNER JOIN users usr
          ON msg.user_id = usr.id
        WHERE
          msg.is_deleted = 0
        ORDER BY
          msg.id
        """
    )
    fun selectAll(): List<MessageDisplayDto>?
}
