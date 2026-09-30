# URL Shortener

REST API сервис для сокращения URL. Написан на Java + Spring Boot + PostgreSQL.

## Стек
- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven

## Как работает
1. Отправляешь длинный URL → сервис сохраняет его в БД и возвращает короткий код
2. Короткий код = Base62-кодирование от `(id XOR secret)`
3. По короткому коду — редирект на оригинальный URL

## Запуск

Создай `.env` файл (или задай переменные окружения):
```
DB_URL=jdbc:postgresql://localhost:5432/url_shortener
DB_USERNAME=postgres
DB_PASSWORD=your_password
APP_SECRET=any_long_number
```

Затем:
```bash
mvn spring-boot:run
```

## API

| Метод | Путь | Описание |
|-------|------|----------|
| POST | `/url` | Создать короткий URL |
| GET | `/{shortUrl}` | Редирект на длинный URL |
| GET | `/url/{id}` | Получить запись по ID |
| GET | `/long_url` | Все записи (с пагинацией) |
| DELETE | `/url/{id}` | Удалить запись |

### Пример запроса
```json
POST /url
{
  "longUrl": "https://example.com/very/long/url"
}
```
