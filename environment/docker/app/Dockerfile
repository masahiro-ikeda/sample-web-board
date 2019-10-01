# 使うDockerイメージ
FROM openjdk:11

# ポートを開ける
EXPOSE 8080

# マウントしてデータを永続化
VOLUME /data

# 起動用ディレクトリを作成
RUN mkdir /app

# jarファイルをコピー
ADD ./app.jar app/app.jar

# docker runに実行される
CMD ["java", "-jar", "app/app.jar"]