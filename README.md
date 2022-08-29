# it-events-aggregator
Агрегатор IT событий

## Запуск
    docker-compose up

## Запуск только окружения
    docker-compose -f docker-compose-env-only.yml up

## Web-интерфейс
http://localhost/

## Начало парсинга
* Заходим страницу http://localhost:15672
* Логинимся (по умолчанию логин и пароль admin)
* Переходим в Exchange с именем it-events-requests-exchange <br /> http://localhost:15672/#/exchanges/%2F/it-events-requests-exchange
* Переходим к пункту Publish message
* Пишем любое сообщение и нажимаем кнопку Publish message