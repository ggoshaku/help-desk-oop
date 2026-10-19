# help-desk-oop

Практическая работа №1 по ООП на Java: консольная модель Help Desk.
Клиент создаёт заявку, специалист берёт её в работу, решает и закрывает, на каждом шаге отправляется уведомление.

Java 17, Maven, без Spring.

## Запуск

```
./mvnw compile exec:java
```

Тесты (JUnit 5):

```
./mvnw test
```

Или открыть проект в IntelliJ IDEA и запустить `helpdesk.Main`.

## Структура

```
src/main/java/helpdesk
├── Main.java
├── exception   InvalidTicketStateException, TicketNotFoundException
├── model       Ticket, TicketStatus, TicketPriority, TicketCategory, StatusChange,
│               User, Customer, SupportAgent, Administrator
├── repository  TicketRepository, InMemoryTicketRepository
└── service     TicketService, NotificationService (Console / Email / Telegram), AuditService
```

## Жизненный цикл заявки

```
NEW -> IN_PROGRESS -> RESOLVED -> CLOSED
```

Отменить (`CANCELLED`) можно любую заявку, кроме закрытой и уже отменённой.
Переходы проверяет сам `Ticket`. При недопустимом переходе выбрасывается `InvalidTicketStateException`.

## Выполненные задания

Основная часть: задания 1–12 и итоговый сценарий.

Самостоятельная работа:

- уровень 1: 1–5 (приоритет, CANCELLED и `cancel()`, проверка title, `createdAt`, `toString()`);
- уровень 2: 6–12 (`assignTicket`, `findById` с `Optional`, `findByStatus`, `findByPriority`,
  Email и Telegram уведомления, статистика, ограничение действий клиента через типы);
- уровень 3: 13–16, 18–20 (SLA и deadline, история статусов, интерфейс репозитория,
  свои исключения, просроченные заявки, `AuditService`, unit-тесты).

Творческое задание: вариант A, IT-служба университета. У заявки есть категория
(`NETWORK`, `SOFTWARE`, `HARDWARE`, `ACCOUNT`), репозиторий умеет фильтровать по ней.

## Принципы ООП в проекте

| Принцип      | Где                                                                                   |
|--------------|---------------------------------------------------------------------------------------|
| Инкапсуляция | private-поля `Ticket`, статус меняется только через `startProcessing/resolve/close/cancel` |
| Наследование | `Customer`, `SupportAgent`, `Administrator` extends `User`                             |
| Полиморфизм  | `List<User>` + `user.performAction()`, разные реализации `NotificationService`         |
| Абстракция   | abstract `User`, интерфейсы `NotificationService` и `TicketRepository`                 |
