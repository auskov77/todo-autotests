
# Тестовое задание

Прикрепленное изображение содержит приложение, реализующее самый простой менеджер TODO с операциями CRUD. Образ можно загрузить с помощью «docker load» и запустить с помощью «docker run -p 8080:4242».

Задание:

Необходимо написать несколько тестов для проверки работоспособности приложения. Мы не даем строгих спецификаций, потому что домен достаточно прост. Поэтому приходится придумывать кейсы самостоятельно.




## Предусловия

1. Для работы необходимо использовать Docker
   - для проверки установленного ПО набрать в командной строке: docker version
2. Запустить контейнер
   - команда в терминале: docker start [Name container]


## Реализация

Реализовал фреймворк на основе: 
Java 11, Maven, Junit5, RestAssured, AssertJ, Allure Report.

Для логирования использовал Log4j и Slf4j.

Создал 2 группы тестов: позитивные и негативные. Тесты проверяют методы HTTP: Get, Post, Put, Delete. Тесты проходят в многопотчном режиме.


## Запуск тестов

Параметры для проверки методов login и password задаются через переменные окружения или через переменные с параметром -D

Пример запуска тестов:

```bash
mvn clean test -Dlogin=... -Dpassword=...
```

После прогона тестов формировал результаты прогона:
 - для создания отчетов:
 ```bash
 mvn allure:report
 ```

 - для отображения результатов прогона в формате HTML:
 ```bash
 mvn allure:serve
 ```
## Нагрузочное тестирование

  Нагрузочное тестирование проводилось с использованием инструмента JMeter.

  Анализ результатов проводился на основе средств JMeter.

  Цель тестирования: определить неоптимальные параметры работы приложения.

  Тестирование проводилось для метода POST с созданием новых todo с постоянным полем "text" и разными значениями поля "completed" (true/false).

  Создано 11 тестовых групп.
#### Для каждой группы созданы:
  1.	HTTP Header Manager, где Content-Type: application/json
  2.	CounterID, где Exported Variable Name: id; Increment: 1
  3.	User Defined Variables, где true: true, false: false
#### Для каждой тестовой группы были приняты следующие общие данные:
  - Web Server: Protocol – http
  - Web Server: Server Name – localhost
  - Web Server: Port Number – 8080
  - HTTP request: Path - /todos/5
  - HTTP request - POST
  - Body Data: 

  {
  
    "id": ${id},
    "text": "TODO",
    "completed": ${__RandomFromMultipleVars(true|false)}
  }
  -	Thread Properties: Ramp-up period (second) – 10
  -	Thread Properties: Loop Count: 1

#### Тестовые группы - параметры:
  1.	Thread Properties: Number of Threads (users) – 20
  2.	Thread Properties: Number of Threads (users) – 50
  3.	Thread Properties: Number of Threads (users) – 100
  4.	Thread Properties: Number of Threads (users) – 200
  5.	Thread Properties: Number of Threads (users) – 500
  6.	Thread Properties: Number of Threads (users) – 700
  7.	Thread Properties: Number of Threads (users) – 1000
  8.	Thread Properties: Number of Threads (users) – 1500
  9.	Thread Properties: Number of Threads (users) – 1700
  10.	Thread Properties: Number of Threads (users) – 2000
  11.	Thread Properties: Number of Threads (users) – 5000


#### Пример запуска нагрузочных тестов:

```bash
  mvn clean verify -Dlogin=... -Dpassword=...
```

#### Результаты тестов:

| N п/п | Name     | Samples | Average, ms | Min, ms | Max, ms | Std. Dev. | Throughput |
| :---- | :------- | :------ | :---------- | :------ | :------ | :-------- | :--------- |
| 1 | TG Post [20, 10]     | 20 | 16 | 8 | 37 | 6.23 | 2.1/sec |
| 2 | TG Post [50, 10]     | 50 | 15 | 6 | 58 | 8.36 | 5.1/sec |
| 3 | TG Post [100, 10]     | 100 | 14 | 6 | 35 | 5.07 | 10.1/sec |
| 4 | TG Post [200, 10]     | 200 | 9 | 5 | 43 | 5.03 | 20.0/sec |
| 5 | TG Post [500, 10]     | 500 | 11 | 5 | 89 | 11.09 | 50.0/sec |
| 6 | TG Post [700, 10]     | 700 | 23 | 5 | 270 | 31.46 | 71.0/sec |
| 7 | TG Post [1000, 10]     | 1000 | 41 | 6 | 462 | 48.72 | 98.9/sec |
| 8 | TG Post [1500, 10]     | 1500 | 126 | 7 | 975 | 132.20 | 139.0/sec |
| 9 | TG Post [1700, 10]     | 1700 | 197 | 6 | 999 | 160.86 | 161.0/sec |
| 10 | TG Post [2000, 10]     | 2000 | 449 | 6 | 1845 | 379.97 | 192.4/sec |
| 11 | TG Post [5000, 10]     | 5000 | 6634 | 2679 | 13871 | 2199.06 | 197.5/sec |

#### Выводы:

Из сводной таблицы результатов тестов видим, что:

1. начиная с теста № 10 показатели Average, Max, Std.Dev. начинают сильно возрастать с аналогичными показателями тестов №№ 1...9
2. показатель Throughput в тестах №№ 8...10 незначительно хуже аналогичных показателе для тестов №№ 1...7 - примерно на 8%
3. показатели теста № 11, такие как - Average, Min, Max, Std.Dev. и Throughput, начинают очень сильно отличаться в худшую сторону в сравнении с аналогичными показателями тестов №№ 1...10. Ухудшение следующие (в сравнении с тестом № 10): Average - в 14.7 раз, Min - в 446.5 раз, Max - в 7.5 раз, Std.Dev. - в 5.8 раз.
Показатель Throughput показывает, что при добавлении 500 пользователей за 10 сек., в среднем в секунду добавлялось 192.5 пользователя, т.е. ухудшение составляет порядка 39.5%.

Итак, при добавлении пользователей в количестве более 2000 максимальное время отклика будет составлять более 1.9 сек (при 5000 - 13.9 сек), т.е. ухудшаются не тольео показатели Max, но и как следствие показатели Min, Average и Throughput. И как следствие работа приложения для пользователей в количестве более 2000 будет не оптимальной.
## 🚀 Автор
Усков Александр Петрович

