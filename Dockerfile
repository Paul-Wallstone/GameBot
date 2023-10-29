FROM openjdk:17

LABEL version="1.0"
LABEL image_name="game_bot"
# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

ENV BOT_NAME choose_one_game_bot
ENV BOT_TOKEN 6869385153:AAEV4otuyuHtF17wMQitjUKxngieEldb8_w
ENV BOT_URI https://cf56-109-236-81-180.ngrok-free.app

# Копируем JAR-файл внутрь контейнера
COPY build/libs/GameBot-1.0-SNAPSHOT.jar GameBot-1.0-SNAPSHOT.jar

# Выбираем порт, который будет доступен извне контейнера
EXPOSE 8084

# Запускаем приложение
CMD java -jar GameBot-1.0-SNAPSHOT.jar
