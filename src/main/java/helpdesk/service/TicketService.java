package helpdesk.service;

import helpdesk.exception.TicketNotFoundException;
import helpdesk.model.Customer;
import helpdesk.model.SupportAgent;
import helpdesk.model.Ticket;
import helpdesk.model.TicketStatus;
import helpdesk.model.User;
import helpdesk.repository.TicketRepository;

import java.util.EnumMap;
import java.util.Map;

public class TicketService {
    private final TicketRepository repository;
    private final NotificationService notificationService;

    public TicketService(TicketRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void registerTicket(Ticket ticket, Customer customer) {
        repository.add(ticket);
        notificationService.send("Заявка №" + ticket.getId() + " от клиента " + customer.getName() + " зарегистрирована");
    }

    public Ticket getTicket(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
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

    public Map<TicketStatus, Long> getStatistics() {
        Map<TicketStatus, Long> statistics = new EnumMap<>(TicketStatus.class);
        for (TicketStatus status : TicketStatus.values()) {
            statistics.put(status, 0L);
        }
        for (Ticket ticket : repository.findAll()) {
            statistics.merge(ticket.getStatus(), 1L, Long::sum);
        }
        return statistics;
    }
}
