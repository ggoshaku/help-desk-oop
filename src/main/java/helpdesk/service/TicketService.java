package helpdesk.service;

import helpdesk.model.SupportAgent;
import helpdesk.model.Ticket;
import helpdesk.model.User;

public class TicketService {
    private final NotificationService notificationService;

    public TicketService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void startTicket(Ticket ticket, SupportAgent agent) {
        agent.assignTicket(ticket);
        notificationService.send("Заявка №" + ticket.getId() + " принята в работу");
    }

    public void resolveTicket(Ticket ticket, SupportAgent agent) {
        ticket.resolve();
        notificationService.send("По заявке №" + ticket.getId() + " найдено решение");
    }

    public void closeTicket(Ticket ticket, SupportAgent agent) {
        ticket.close();
        notificationService.send("Заявка №" + ticket.getId() + " закрыта");
    }

    public void cancelTicket(Ticket ticket, User initiator) {
        ticket.cancel();
        notificationService.send("Заявка №" + ticket.getId() + " отменена пользователем " + initiator.getName());
    }
}
