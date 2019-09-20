# スキーマ
CREATE DATABASE develop;

# ユーザー
  CREATE TABLE develop.users(
      id         VARCHAR(100) NOT NULL,
      password   VARCHAR(100) NOT NULL,
      name       VARCHAR(100) NOT NULL,
      role       VARCHAR(5) NOT NULL,
      is_invalid INT(1) NOT NULL,
      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );

  # 掲示板
  CREATE TABLE develop.messages(
      id            VARCHAR(10) NOT NULL PRIMARY KEY,
      type          VARCHAR(10) NOT NULL,
      post_no       INT(5) NOT NULL,
      reply_no      INT(5) NOT NULL,
      user_id       VARCHAR(100) NOT NULL,
      comment       VARCHAR(400) NOT NULL,
      is_deleted    INT(1) NOT NULL,
      created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );