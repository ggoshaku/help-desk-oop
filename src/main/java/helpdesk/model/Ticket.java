package helpdesk.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ticket {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final long id;
    private final String title;
    private final String description;
    private final TicketPriority priority;
    private final LocalDateTime createdAt;
    private TicketStatus status;

    public Ticket(long id, String title, String description, TicketPriority priority) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название заявки не может быть пустым");
        }
        this.id = id;
        this.title = title.strip();
        this.description = description;
        this.priority = Objects.requireNonNull(priority, "priority");
        this.createdAt = LocalDateTime.now();
        this.status = TicketStatus.NEW;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void startProcessing() {
        if (status != TicketStatus.NEW) {
            System.out.println("Ошибка: в работу можно взять только новую заявку");
            return;
        }
        status = TicketStatus.IN_PROGRESS;
    }

    public void resolve() {
        if (status != TicketStatus.IN_PROGRESS) {
            System.out.println("Ошибка: решить можно только заявку в работе");
            return;
        }
        status = TicketStatus.RESOLVED;
    }

    public void close() {
        if (status != TicketStatus.RESOLVED) {
            System.out.println("Ошибка: закрыть можно только решённую заявку");
            return;
        }
        status = TicketStatus.CLOSED;
    }

    @Override
    public String toString() {
        return "#" + id + " " + title
                + " | " + status
                + " | " + priority
                + " | создана " + createdAt.format(DATE_FORMAT);
    }
}
