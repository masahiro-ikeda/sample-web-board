# 未使用

# 使うDockerイメージ
FROM openjdk:11

# ポートを開ける
EXPOSE 8080

# 起動用ディレクトリを作成
RUN mkdir /application

# jarファイルをコピー
ADD ./sample-web-board.jar /application/sample-web-board.jar

# docker runに実行される
CMD ["java", "-jar", "/application/sample-web-board.jar"]