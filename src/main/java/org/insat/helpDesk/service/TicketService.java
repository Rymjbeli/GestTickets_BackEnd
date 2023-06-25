package org.insat.helpDesk.service;

import org.insat.helpDesk.Model.Ticket;
import org.insat.helpDesk.Repository.TicketRepository;

import java.util.List;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket addTicket(Ticket ticket) {
        ticket.setStatus("Open");
        ticket.setDate(new java.util.Date());
        ticket.setModif(null);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket findTicketById(Long id) {
        return ticketRepository.findTicketById(id);
    }

    public List<Ticket> findTicketsByStatus(String status) {
        return ticketRepository.findTicketsByStatus(status);
    }
}
