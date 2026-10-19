package helpdesk;

import helpdesk.model.Administrator;
import helpdesk.model.Customer;
import helpdesk.model.SupportAgent;
import helpdesk.model.Ticket;
import helpdesk.model.TicketPriority;
import helpdesk.model.User;
import helpdesk.repository.TicketRepository;
import helpdesk.service.ConsoleNotificationService;
import helpdesk.service.NotificationService;
import helpdesk.service.TicketService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HELP DESK ===");

        Customer customer = new Customer(1, "Анна Петрова", "anna@mail.ru");
        SupportAgent agent = new SupportAgent(2, "Сергей Иванов", "sergey@helpdesk.ru");
        Administrator admin = new Administrator(3, "Олег Смирнов", "admin@helpdesk.ru");

        NotificationService notificationService = new ConsoleNotificationService();
        TicketService ticketService = new TicketService(notificationService);
        TicketRepository repository = new TicketRepository();

        System.out.println("Клиент: " + customer.getName());

        Ticket ticket = new Ticket(1, "Не работает Wi-Fi", "Ноутбук не видит сеть в аудитории 305", TicketPriority.HIGH);
        printTicket(ticket);

        ticketService.startTicket(ticket);
        printStatus(ticket);
        ticketService.resolveTicket(ticket);
        printStatus(ticket);
        ticketService.closeTicket(ticket);
        printStatus(ticket);

        repository.add(ticket);
        repository.add(new Ticket(2, "Ошибка приложения", "1С падает при формировании отчёта", TicketPriority.MEDIUM));
        Ticket printerTicket = new Ticket(3, "Не печатает принтер", "Принтер в бухгалтерии пишет \"замятие бумаги\"", TicketPriority.LOW);
        repository.add(printerTicket);
        ticketService.cancelTicket(printerTicket);

        System.out.println();
        System.out.println("Все заявки:");
        printTickets(repository.findAll());

        System.out.println();
        try {
            new Ticket(4, "   ", "Без названия", TicketPriority.LOW);
        } catch (IllegalArgumentException e) {
            System.out.println("Заявка не создана: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Пользователи:");
        for (User user : List.of(customer, agent, admin)) {
            user.performAction();
        }
    }

    private static void printTicket(Ticket ticket) {
        System.out.println("Заявка #" + ticket.getId() + ": " + ticket.getTitle() + " | " + ticket.getStatus());
    }

    private static void printStatus(Ticket ticket) {
        System.out.println("Статус: " + ticket.getStatus());
    }

    private static void printTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}
