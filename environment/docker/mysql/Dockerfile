# 使うDockerイメージ
FROM mysql:5.7

# ポートを開ける
EXPOSE 3306

# 初期ユーザの作成
ENV MYSQL_ROOT_PASSWORD=pass
ENV MYSQL_USER=dev-user
ENV MYSQL_PASSWORD=dev-pass
ENV MYSQL_DATABASE=develop

# MySQL設定ファイルをイメージ内にコピー
ADD ./my.cnf /etc/mysql/conf.d/my.cnf

# テーブル初期化SQLをコピー
ADD ./init/create.sql /docker-entrypoint-initdb.d/create.sql

# マウントしてデータを永続化
VOLUME /data

# docker runに実行される
CMD ["mysqld"]