package helpdesk.model;

public class Ticket {
    private long id;
    private String title;
    private String description;
    private String status;

    public Ticket(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "NEW";
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

    public String getStatus() {
        return status;
    }

    public void startProcessing() {
        if (!status.equals("NEW")) {
            System.out.println("Ошибка: в работу можно взять только новую заявку");
            return;
        }
        status = "IN_PROGRESS";
    }

    public void resolve() {
        if (!status.equals("IN_PROGRESS")) {
            System.out.println("Ошибка: решить можно только заявку в работе");
            return;
        }
        status = "RESOLVED";
    }

    public void close() {
        if (!status.equals("RESOLVED")) {
            System.out.println("Ошибка: закрыть можно только решённую заявку");
            return;
        }
        status = "CLOSED";
    }
}
