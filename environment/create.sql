# スキーマ
CREATE DATABASE develop;

# ユーザー
  CREATE TABLE develop.users(
      id          VARCHAR(25) NOT NULL,
      password    VARCHAR(25) NOT NULL,
      name        VARCHAR(25) NOT NULL,
      role        VARCHAR(10) NOT NULL,
      is_invalid  INT(1) NOT NULL,
      created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );

  # メッセージ
  CREATE TABLE develop.messages(
      id          VARCHAR(36) NOT NULL PRIMARY KEY,
      type        VARCHAR(10) NOT NULL,
      post_no     INT(5) NOT NULL,
      reply_no    INT(5) NOT NULL,
      user_id     VARCHAR(25) NOT NULL,
      comment     VARCHAR(100) NOT NULL,
      is_deleted  INT(1) NOT NULL,
      created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );

  # いいね
  CREATE TABLE develop.goods(
      id          VARCHAR(36) NOT NULL PRIMARY KEY,
      message_id  VARCHAR(36) NOT NULL,
      user_id     VARCHAR(25) NOT NULL,
      is_deleted  INT(1) NOT NULL,
      created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );