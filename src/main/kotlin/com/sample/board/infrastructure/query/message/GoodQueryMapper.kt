package com.sample.board.infrastructure.domain.message

import com.sample.board.domain.message.Good
import com.sample.board.query.dto.MessageDto
import com.sample.board.domain.message.Message
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface GoodQueryMapper {

    @Select(
        """
        <script>
        SELECT
          id,
          message_id,
          user_id,
          is_deleted
        FROM
          goods
        <where>
          <if test="messageId != null">
            message_id = #{messageId}
          </if>
          is_deleted = 0
        </where>
        </script>
        """)
    fun select(messageId: String?): List<Good>?
}
