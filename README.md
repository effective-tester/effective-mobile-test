# Task Management System

**TMS** - простая система управления задачами. В Spring Boot приложении реализованы:
1. **Аутентификация** и **авторизация** пользователей по email и паролю с помощью `JWT`
2. **Ролевая система** с разграничением доступа
3. **Фильтрация, пагинация** и **валидация** входящих данных
4. **Логгирование** с помощью аспектов и записи в файлы, а также **обработка ошибок** в контроллер-адвайсе
5. **Документация** с помощью `Swagger` (см. ниже)
6. **Контеризация** средствами `Docker` и **тестирование** с генерацие отчета с помощью `JaCoCo`

# Локальный запуск проекта

Проект состоит из нескольких модулей, включая Spring Boot приложение и PostgreSQL базу данных, запускаемых в отдельных контейнерах

## Требования
`Docker` и `Docker Compose` 
## Настройка

1. **Получите локальную копию репозитория:**

   Склонируйте репозиторий:

   ```bash
   git clone https://github.com/effective-tester/effective-mobile-test.git
   ```
   или скачайте и разархивируйте приложение

2. **Настройте файл `.env`:**

   В корневой директории проекта создайте файл `.env` и добавьте Ваши переменные среды, например, следующие:

    ```env
   DB_SERVER=tms-bd
   DB_HOST_PORT=5434
   DB_SERVER_PORT=5432
   DB_NAME=task
   DB_USERNAME=postgres
   DB_PASSWORD=postgres
   
   HOST_PORT=8080
   SERVER_PORT=8080
   
   JWT_SECRET=3ZbN0C2xVh2x7O1YsZ3qLwKX48eAG6E1+H6QiKlf3LJq4H3YfZIuqpeIDtTsVzT4jk=
   JWT_EXPIRATION=86400000
   JWT_HEADER=Authorization
   JWT_PREFIX="Bearer "
    ```

## Запуск проекта

Выполните следующие команды для сборки и запуска контейнеров:

```bash
docker-compose build
docker-compose up
```

или используйте аналогичный вариант:

```bash
docker-compose up --build
```

# Swagger

Вы можете локально ознакомиться с документацией `api`, если перейдете по ссылке:

```
http://localhost:${HOST_PORT}/api/v1/swagger-ui/index.html
```

Либо воспользуйтесь любыми средствами визуализация `openapi.yml` файла (например, с помощью `IntelliJ IDEA`), который имеет путь:
```
backend\src\main\resources\static\openapi.yml
```

# JaCoCo

Вы можете запустить тесты и получить отчет:

```bash
./gradlew clean test jacocoTestReport
```

после чего откройте `index.html`, который имеет путь:
```
backend\build\reports\jacoco\test\html\index.html
```


