# ユーザー
CREATE TABLE users(
    id          VARCHAR(25) NOT NULL,
    password    VARCHAR(60) NOT NULL,
    name        VARCHAR(25) NOT NULL,
    role        VARCHAR(10) NOT NULL,
    is_invalid  INT(1) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

# メッセージ
CREATE TABLE messages(
    id          INT unsigned AUTO_INCREMENT NOT NULL PRIMARY KEY,
    type        VARCHAR(10) NOT NULL,
    reply_to    INT unsigned,
    user_id     VARCHAR(25) NOT NULL,
    comment     VARCHAR(100) NOT NULL,
    is_deleted  INT(1) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

# いいね
CREATE TABLE goods(
    id          INT unsigned AUTO_INCREMENT NOT NULL PRIMARY KEY,
    message_id  INT NOT NULL,
    user_id     VARCHAR(25) NOT NULL,
    is_deleted  INT(1) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);