package com.sample.board.api.infrastructure.query.message

import com.sample.board.api.domain.entity.message.Good
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface GoodQueryMapper {

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
          message_id = #{messageId} AND
          is_deleted = 0
        """
    )
    fun selectByMessageId(messageId: String): MutableList<Good>?

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
    fun selectAll(): List<Good>?
}
