1. Настройка локальной БД (система windows)
1. Установить postgres версии 10.3.3 
2. Порт по умолчанию 5432, каталог для хранения данных ..../data
3. Задать для пользователя по умолчанию postgres пароль admin
4. Если сервер не работает как служба в windows. Использовать утилиту pg_ctl.exe start -D ..../data для запуска сервера.
5. Запустить pgAdmin4.exe
6. Создать database c именем regme

2. Запуск приложения
Запустить $ mvn spring-boot:run
В браузере перейти на http://localhost:8888//swagger-ui.html
Чтобы обновить справочник 
Нажать на кнопку Try it out на вкладке
http://localhost:8888//swagger-ui.html#!/fms-controller-api/updateOrganizationUsingPOST
Чтобы получить описание отделения, по коду 
Открыть вкладку 
http://localhost:8888//swagger-ui.html#!/fms-controller-api/fmsByCodeUsingGET
Заполнить строку code например 580-002. Нажать на кнопку Try it out на вкладке

