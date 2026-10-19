package helpdesk;

import helpdesk.model.Administrator;
import helpdesk.model.Customer;
import helpdesk.model.SupportAgent;
import helpdesk.model.Ticket;
import helpdesk.model.User;

import java.util.ArrayList;
import java.util.List;

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

        List<User> users = new ArrayList<>();
        users.add(new Customer(1, "Анна", "anna@mail.ru"));
        users.add(new SupportAgent(2, "Сергей", "sergey@helpdesk.ru"));
        users.add(new Administrator(3, "Олег", "admin@helpdesk.ru"));

        for (User user : users) {
            user.performAction();
        }
    }
}
