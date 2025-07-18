# bankapp

Spring-приложение онлайн банкинг

Инструкции для запуска в Docker:
1) Клонировать проект через git clone
2) Запустить Docker
3) Запсутить контейнер из docker-compose-keycloak.yml
4) Когда поднимется контейнер keycloak зайти в панель администратора http://localhost:8085
   с кредами admin/admin
5) Создать клиентов с id с префиксом "bankapp-" и постфиксами front, exchange, transfer
   и настройками https://pictures.s3.yandex.net/resources/image_23_1743002431.png
6) На вкладке Credentials для каждого клиента скопировать Client Secret и вставить в файлы с
   соответствующими именами клиентов в config-server/src/main/resources/config-repo в переменные 
   KEYCLOAK_CLIENT_SECRET
7) Выполнить mvn clean package
8) Выполнить docker compose up
9) Приложение будет доступно на http://localhost:8080
10) Вход по логинам "user1@email.net" и "user2@email.net", пароли "123"