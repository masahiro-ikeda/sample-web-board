# 使うDockerイメージ
FROM mysql:5.7

# ポートを開ける
EXPOSE 3306

# MySQL設定ファイルをイメージ内にコピー
ADD ./my.cnf /etc/mysql/conf.d/my.cnf

# マウントしてデータを永続化
VOLUME /data

# docker runに実行される
CMD ["mysqld"]