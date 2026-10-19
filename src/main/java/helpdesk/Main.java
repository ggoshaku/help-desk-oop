package helpdesk;

import helpdesk.model.Ticket;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HELP DESK ===");

        Ticket ticket = new Ticket(
                1,
                "Не работает интернет",
                "После перезагрузки компьютера пропало подключение"
        );

        System.out.println("Заявка #" + ticket.getId() + ": " + ticket.getTitle() + " | " + ticket.getStatus());

        ticket.resolve();

        ticket.startProcessing();
        System.out.println("Статус: " + ticket.getStatus());
        ticket.resolve();
        System.out.println("Статус: " + ticket.getStatus());
        ticket.close();
        System.out.println("Статус: " + ticket.getStatus());
    }
}
