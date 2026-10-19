package helpdesk.repository;

import helpdesk.model.Ticket;
import helpdesk.model.TicketPriority;
import helpdesk.model.TicketStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TicketRepository {
    private final List<Ticket> tickets = new ArrayList<>();

    public void add(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets);
    }

    public Optional<Ticket> findById(long id) {
        return tickets.stream()
                .filter(ticket -> ticket.getId() == id)
                .findFirst();
    }

    public List<Ticket> findByStatus(TicketStatus status) {
        return findBy(ticket -> ticket.getStatus() == status);
    }

    public List<Ticket> findByPriority(TicketPriority priority) {
        return findBy(ticket -> ticket.getPriority() == priority);
    }

    private List<Ticket> findBy(Predicate<Ticket> condition) {
        return tickets.stream()
                .filter(condition)
                .toList();
    }
}
