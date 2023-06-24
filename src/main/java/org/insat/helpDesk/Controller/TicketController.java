package org.insat.helpDesk.Controller;

import org.insat.helpDesk.Repository.TicketRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.insat.helpDesk.Model.User;
import org.insat.helpDesk.Exception.ResourceNotFoundException;
import org.insat.helpDesk.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping("all")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();  
    }
    @GetMapping("{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        Ticket ticket= ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User with id " + id + " not found."));
        User user = ticket.getUser();
        ticket.setUser(user);  
        return ticket;                
    }

    @PutMapping("{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketToUpdate = ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User with id " + id + " not found."));
        ticketToUpdate.setTitle(ticket.getTitle());
        ticketToUpdate.setContent(ticket.getContent());
        ticketToUpdate.setPriority(ticket.getPriority());
        ticketToUpdate.setStatus(ticket.getStatus());
        ticketToUpdate.setDate(ticket.getDate());
        ticketToUpdate.setModif(ticket.getModif());
        return ticketRepository.save(ticketToUpdate);
    }

    @DeleteMapping("delete/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketRepository.deleteById(id);
    }

}